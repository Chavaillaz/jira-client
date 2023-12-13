package com.chavaillaz.client.jira.java;

import static com.chavaillaz.client.jira.JiraConstants.BASE_API;

import java.net.http.HttpClient;

import com.chavaillaz.client.common.java.JavaHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
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
        return JavaHttpUtils.defaultHttpClientBuilder(proxy).build();
    }

    @Override
    public IssueApi<I> getIssueApi() {
        return issueApi.get(() -> new JavaHttpIssueApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new JavaHttpProjectApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new JavaHttpUserApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new JavaHttpSearchApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
