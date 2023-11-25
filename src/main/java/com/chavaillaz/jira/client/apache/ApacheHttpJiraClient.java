package com.chavaillaz.jira.client.apache;

import com.chavaillaz.client.apache.ApacheHttpUtils;
import com.chavaillaz.jira.client.AbstractJiraClient;
import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.client.JiraClient;
import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

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
        return ApacheHttpUtils.newHttpClient(proxy);
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return cacheIssueClient.get(() -> new ApacheHttpIssueClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectClient getProjectClient() {
        return cacheProjectClient.get(() -> new ApacheHttpProjectClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return cacheUserClient.get(() -> new ApacheHttpUserClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        return cacheSearchClient.get(() -> new ApacheHttpSearchClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
