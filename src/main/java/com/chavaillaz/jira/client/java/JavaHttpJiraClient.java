package com.chavaillaz.jira.client.java;

import java.net.http.HttpClient;

import com.chavaillaz.client.java.JavaHttpUtils;
import com.chavaillaz.jira.client.AbstractJiraClient;
import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.client.JiraClient;
import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;

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
        return JavaHttpUtils.newHttpClient(proxy);
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return cacheIssueClient.get(() -> new JavaHttpIssueClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectClient getProjectClient() {
        return cacheProjectClient.get(() -> new JavaHttpProjectClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return cacheUserClient.get(() -> new JavaHttpUserClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        return cacheSearchClient.get(() -> new JavaHttpSearchClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
