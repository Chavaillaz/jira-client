package com.chavaillaz.client.jira.apache;

import static com.chavaillaz.client.jira.JiraConstants.BASE_API;

import com.chavaillaz.client.common.apache.ApacheHttpUtils;
import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

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
        return issueApi.get(() -> new ApacheHttpIssueApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new ApacheHttpProjectApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new ApacheHttpUserApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new ApacheHttpSearchApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }

}
