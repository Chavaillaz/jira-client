package com.chavaillaz.jira.client.apache;

import com.chavaillaz.jira.client.*;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.core5.http.HttpHost;

import java.util.List;
import java.util.Optional;

public class ApacheHttpJiraClient<I extends Issue, L extends List<? extends I>> extends AbstractJiraClient<CloseableHttpAsyncClient, I, L> {

    private final Class<I> issueClass;
    private final Class<L> issuesClass;

    /**
     * Creates a new {@link JiraClient} with Apache HTTP client with the given issues and its list.
     *
     * @param jiraUrl The Jira URL
     * @param issueClass The issue class type
     * @param issuesClass The issues list class type
     */
    public ApacheHttpJiraClient(String jiraUrl, Class<I> issueClass, Class<L> issuesClass) {
        super(jiraUrl);
        this.issueClass = issueClass;
        this.issuesClass = issuesClass;
    }

    /**
     * Creates a default {@link JiraClient} with Apache HTTP client.
     *
     * @param jiraUrl The Jira URL
     */
    public static ApacheHttpJiraClient<Issue, Issues> jiraApacheClient(String jiraUrl) {
        return new ApacheHttpJiraClient<>(jiraUrl, Issue.class, Issues.class);
    }

    @Override
    public CloseableHttpAsyncClient newHttpClient() {
        return HttpAsyncClientBuilder.create()
                .useSystemProperties()
                .setProxy(Optional.ofNullable(proxy)
                        .map(config -> new HttpHost(config.getScheme(), config.getHost(), config.getPort()))
                        .orElse(null))
                .build();
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return new ApacheHttpIssueClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issueClass);
    }

    @Override
    public ProjectClient getProjectClient() {
        return new ApacheHttpProjectClient(newHttpClient(), jiraUrl + BASE_API, authentication);
    }

    @Override
    public UserClient getUserClient() {
        return new ApacheHttpUserClient(newHttpClient(), jiraUrl + BASE_API, authentication);
    }

    @Override
    public SearchClient<L> getSearchClient() {
        return new ApacheHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesClass);
    }

}
