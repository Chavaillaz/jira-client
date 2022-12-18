package com.chavaillaz.jira.client.java;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.util.Optional;

import com.chavaillaz.jira.client.AbstractJiraClient;
import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.client.JiraClient;
import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;

public class JavaHttpJiraClient extends AbstractJiraClient<HttpClient> {

    /**
     * Creates a new {@link JiraClient} with Java HTTP client.
     *
     * @param jiraUrl The Jira URL
     */
    public JavaHttpJiraClient(String jiraUrl) {
        super(jiraUrl);
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
    public <T extends Issue> IssueClient<T> getIssueClient(Class<T> issueClass) {
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
    public <T extends Issues> SearchClient<T> getSearchClient(Class<T> issuesClass) {
        return new JavaHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesClass);
    }

}
