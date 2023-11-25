package com.chavaillaz.jira.client.okhttp;

import com.chavaillaz.client.okhttp.OkHttpUtils;
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
        return OkHttpUtils.newHttpClient(proxy);
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return cacheIssueClient.get(() -> new OkHttpIssueClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectClient getProjectClient() {
        return cacheProjectClient.get(() -> new OkHttpProjectClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return cacheUserClient.get(() -> new OkHttpUserClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        return cacheSearchClient.get(() -> new OkHttpSearchClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
