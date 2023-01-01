package com.chavaillaz.jira.client.java;

import com.chavaillaz.jira.client.*;
import com.chavaillaz.jira.domain.Issue;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

public class JavaHttpJiraClient<I extends Issue, L extends List<? extends I>> extends AbstractJiraClient<HttpClient, I, L> {

    private final Class<I> issueClass;
    private final Class<L> issuesClass;

    /**
     * Creates a new {@link JiraClient} with Java HTTP client.
     *
     * @param jiraUrl The Jira URL
     * @param issueClass The issue class type
     * @param issuesClass The issues list class type
     */
    public JavaHttpJiraClient(String jiraUrl, Class<I> issueClass, Class<L> issuesClass) {
        super(jiraUrl);
        this.issueClass = issueClass;
        this.issuesClass = issuesClass;
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
        return new JavaHttpIssueClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issueClass);
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
    public SearchClient<L> getSearchClient() {
        return new JavaHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesClass);
    }

}
