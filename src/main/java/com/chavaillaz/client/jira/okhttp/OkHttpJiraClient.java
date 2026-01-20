package com.chavaillaz.client.jira.okhttp;

import com.chavaillaz.client.common.okhttp.OkHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.api.VersionApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import okhttp3.OkHttpClient;

/**
 * Implementation of {@link JiraClient} using OkHttp client.
 * Supports both JIRA REST API v2 and v3 through the {@link #withApiVersion(int)} method.
 *
 * @param <I> The issue type
 */
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
        return OkHttpUtils.defaultHttpClientBuilder(proxy).build();
    }

    @Override
    public IssueApi<I> getIssueApi() {
        return issueApi.get(() -> new OkHttpIssueApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issueType, apiVersion));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new OkHttpProjectApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new OkHttpUserApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new OkHttpSearchApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issuesListType, apiVersion));
    }

    @Override
    public VersionApi getVersionApi() {
        return versionApi.get(() -> new OkHttpVersionApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

}
