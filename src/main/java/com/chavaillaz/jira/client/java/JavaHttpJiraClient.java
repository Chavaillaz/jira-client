package com.chavaillaz.jira.client.java;

import com.chavaillaz.jira.client.*;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.util.Optional;

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
                .build();
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return new JavaHttpIssueClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issueType);
    }

    @Override
    public ProjectClient getProjectClient() {
        return new JavaHttpProjectClient(newHttpClient(), jiraUrl + BASE_API, authentication);
    }

    @Override
    public UserClient getUserClient() {
        return new JavaHttpUserClient(newHttpClient(), jiraUrl + BASE_API, authentication);
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        return new JavaHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesListType);
    }

}
