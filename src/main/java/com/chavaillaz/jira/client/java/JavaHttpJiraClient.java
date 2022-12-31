package com.chavaillaz.jira.client.java;

import com.chavaillaz.jira.client.*;
import com.chavaillaz.jira.domain.Issue;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

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
    public <T extends List<? extends Issue>> SearchClient<T> getSearchClient(Class<T> issuesClass) {
        return new JavaHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesClass);
    }

}
