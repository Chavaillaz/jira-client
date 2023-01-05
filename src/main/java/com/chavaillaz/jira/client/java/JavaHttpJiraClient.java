package com.chavaillaz.jira.client.java;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
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

public class JavaHttpJiraClient<I extends Issue> extends AbstractJiraClient<HttpClient, I> {

    /**
     * Creates a new {@link JiraClient} with Java HTTP client.
     *
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public JavaHttpJiraClient(String jiraUrl, Class<I> issueType) {
        super(jiraUrl, issueType);
    }

    /**
     * Creates a default {@link JiraClient} with Java HTTP client.
     *
     * @param jiraUrl The Jira URL
     */
    public static JavaHttpJiraClient<Issue> jiraJavaClient(String jiraUrl) {
        return new JavaHttpJiraClient<>(jiraUrl, Issue.class);
    }

    @Override
    public HttpClient newHttpClient() {
        return HttpClient.newBuilder()
                .proxy(Optional.ofNullable(this.proxy)
                        .map(config -> ProxySelector.of(new InetSocketAddress(config.getHost(), config.getPort())))
                        .orElse(ProxySelector.getDefault()))
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Override
    public IssueClient<I> getIssueClient() {
        if (cacheIssueClient == null) {
            cacheIssueClient = new JavaHttpIssueClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issueType);
        }
        return cacheIssueClient;
    }

    @Override
    public ProjectClient getProjectClient() {
        if (cacheProjectClient == null) {
            cacheProjectClient = new JavaHttpProjectClient(newHttpClient(), jiraUrl + BASE_API, authentication);
        }
        return cacheProjectClient;
    }

    @Override
    public UserClient getUserClient() {
        if (cacheUserClient == null) {
            cacheUserClient = new JavaHttpUserClient(newHttpClient(), jiraUrl + BASE_API, authentication);
        }
        return cacheUserClient;
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        if (cacheSearchClient == null) {
            cacheSearchClient = new JavaHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesListType);
        }
        return cacheSearchClient;
    }

}
