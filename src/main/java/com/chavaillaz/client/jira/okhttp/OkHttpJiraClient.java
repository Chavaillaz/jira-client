package com.chavaillaz.client.jira.okhttp;

import static com.chavaillaz.client.jira.JiraConstants.BASE_API;

import com.chavaillaz.client.common.okhttp.OkHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueClient;
import com.chavaillaz.client.jira.api.ProjectClient;
import com.chavaillaz.client.jira.api.SearchClient;
import com.chavaillaz.client.jira.api.UserClient;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import okhttp3.OkHttpClient;

public class OkHttpJiraClient<I extends Issue> extends AbstractJiraClient<OkHttpClient, I> {

    /**
     * Creates a new {@link JiraClient} using OkHttp client with the given issue type.
     *
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public OkHttpJiraClient(String jiraUrl, Class<I> issueType) {
        super(jiraUrl, issueType);
    }

    @Override
    public OkHttpClient newHttpClient() {
        return OkHttpUtils.newHttpClient(proxy);
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return issueClient.get(() -> new OkHttpIssueClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectClient getProjectClient() {
        return projectClient.get(() -> new OkHttpProjectClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return userClient.get(() -> new OkHttpUserClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        return searchClient.get(() -> new OkHttpSearchClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
