package com.chavaillaz.jira.client.apache;

import java.util.Optional;

import com.chavaillaz.jira.client.AbstractJiraClient;
import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.client.JiraClient;
import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.core5.http.HttpHost;

public class ApacheHttpJiraClient<I extends Issue> extends AbstractJiraClient<CloseableHttpAsyncClient, I> {

    /**
     * Creates a new {@link JiraClient} with Apache HTTP client with the given issues and its list.
     *
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public ApacheHttpJiraClient(String jiraUrl, Class<I> issueType) {
        super(jiraUrl, issueType);
    }

    /**
     * Creates a default {@link JiraClient} with Apache HTTP client.
     *
     * @param jiraUrl The Jira URL
     */
    public static ApacheHttpJiraClient<Issue> jiraApacheClient(String jiraUrl) {
        return new ApacheHttpJiraClient<>(jiraUrl, Issue.class);
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
        return new ApacheHttpIssueClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issueType);
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
    public SearchClient<Issues<I>> getSearchClient() {
        return new ApacheHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesListType);
    }

}
