package com.chavaillaz.jira.client.okhttp;

import static java.net.Proxy.Type.HTTP;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.Optional;

import com.chavaillaz.jira.client.AbstractJiraClient;
import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.client.JiraClient;
import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;
import okhttp3.OkHttpClient;

public class OkHttpJiraClient<I extends Issue> extends AbstractJiraClient<OkHttpClient, I> {

    /**
     * Creates a new {@link JiraClient} with OkHttp client with the given issues and its list.
     *
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public OkHttpJiraClient(String jiraUrl, Class<I> issueType) {
        super(jiraUrl, issueType);
    }

    /**
     * Creates a default {@link JiraClient} with OkHttp client.
     *
     * @param jiraUrl The Jira URL
     */
    public static OkHttpJiraClient<Issue> jiraOkHttpClient(String jiraUrl) {
        return new OkHttpJiraClient<>(jiraUrl, Issue.class);
    }

    @Override
    public OkHttpClient newHttpClient() {
        return new OkHttpClient.Builder()
                .proxy(Optional.ofNullable(proxy)
                        .map(config -> new Proxy(HTTP, new InetSocketAddress(config.getHost(), config.getPort())))
                        .orElse(null))
                .connectTimeout(Duration.ofSeconds(30))
                .readTimeout(Duration.ofSeconds(30))
                .callTimeout(Duration.ofSeconds(0))
                .build();
    }

    @Override
    public IssueClient<I> getIssueClient() {
        if (cacheIssueClient == null) {
            cacheIssueClient = new OkHttpIssueClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issueType);
        }
        return cacheIssueClient;
    }

    @Override
    public ProjectClient getProjectClient() {
        if (cacheProjectClient == null) {
            cacheProjectClient = new OkHttpProjectClient(newHttpClient(), jiraUrl + BASE_API, authentication);
        }
        return cacheProjectClient;
    }

    @Override
    public UserClient getUserClient() {
        if (cacheUserClient == null) {
            cacheUserClient = new OkHttpUserClient(newHttpClient(), jiraUrl + BASE_API, authentication);
        }
        return cacheUserClient;
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        if (cacheSearchClient == null) {
            cacheSearchClient = new OkHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesListType);
        }
        return cacheSearchClient;
    }

}
