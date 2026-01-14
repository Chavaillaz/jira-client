package com.chavaillaz.client.jira.apache;

import com.chavaillaz.client.common.apache.ApacheHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.api.VersionApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

/**
 * Implementation of {@link JiraClient} using Apache HTTP client.
 * Supports both JIRA REST API v2 and v3 through the {@link #withApiVersion(int)} method.
 *
 * @param <I> The issue type
 */
public class ApacheHttpJiraClient<I extends Issue> extends AbstractJiraClient<CloseableHttpAsyncClient, I> {

    /**
     * Creates a new {@link JiraClient} using Apache HTTP client with the given issue type.
     *
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public ApacheHttpJiraClient(String jiraUrl, Class<I> issueType) {
        super(jiraUrl, issueType);
    }

    @Override
    public CloseableHttpAsyncClient newHttpClient() {
        return ApacheHttpUtils.defaultHttpClientBuilder(proxy).build();
    }

    @Override
    public IssueApi<I> getIssueApi() {
        return issueApi.get(() -> new ApacheHttpIssueApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issueType, apiVersion));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new ApacheHttpProjectApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new ApacheHttpUserApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new ApacheHttpSearchApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issuesListType, apiVersion));
    }

    @Override
    public VersionApi getVersionApi() {
        return versionApi.get(() -> new ApacheHttpVersionApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

}
