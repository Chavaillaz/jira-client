package com.chavaillaz.client.jira.java;

import java.net.http.HttpClient;

import com.chavaillaz.client.common.java.JavaHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.api.VersionApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;

/**
 * Implementation of {@link JiraClient} using Java HTTP client.
 * Supports both JIRA REST API v2 and v3 through the {@link #withApiVersion(int)} method.
 *
 * @param <I> The issue type
 */
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
        return issueApi.get(() -> new JavaHttpIssueApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issueType, apiVersion));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new JavaHttpProjectApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new JavaHttpUserApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new JavaHttpSearchApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issuesListType, apiVersion));
    }

    @Override
    public VersionApi getVersionApi() {
        return versionApi.get(() -> new JavaHttpVersionApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

}
