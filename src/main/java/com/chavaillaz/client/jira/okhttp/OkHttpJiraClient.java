package com.chavaillaz.client.jira.okhttp;

import static com.chavaillaz.client.jira.JiraConstants.BASE_API;

import com.chavaillaz.client.common.okhttp.OkHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
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
    public IssueApi<I> getIssueApi() {
        return issueApi.get(() -> new OkHttpIssueApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new OkHttpProjectApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new OkHttpUserApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new OkHttpSearchApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
