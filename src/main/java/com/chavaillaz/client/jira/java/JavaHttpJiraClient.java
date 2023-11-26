package com.chavaillaz.client.jira.java;

import static com.chavaillaz.client.jira.JiraConstants.BASE_API;

import java.net.http.HttpClient;

import com.chavaillaz.client.common.java.JavaHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueClient;
import com.chavaillaz.client.jira.api.ProjectClient;
import com.chavaillaz.client.jira.api.SearchClient;
import com.chavaillaz.client.jira.api.UserClient;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;

public class JavaHttpJiraClient<I extends Issue> extends AbstractJiraClient<HttpClient, I> {

    /**
     * Creates a new {@link JiraClient} using Java HTTP client with the given issue type.
     *
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public JavaHttpJiraClient(String jiraUrl, Class<I> issueType) {
        super(jiraUrl, issueType);
    }

    @Override
    public HttpClient newHttpClient() {
        return JavaHttpUtils.newHttpClient(proxy);
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return issueClient.get(() -> new JavaHttpIssueClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectClient getProjectClient() {
        return projectClient.get(() -> new JavaHttpProjectClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return userClient.get(() -> new JavaHttpUserClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        return searchClient.get(() -> new JavaHttpSearchClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
