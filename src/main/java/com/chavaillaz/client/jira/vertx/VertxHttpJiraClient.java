package com.chavaillaz.client.jira.vertx;

import static com.chavaillaz.client.common.vertx.VertxUtils.defaultWebClientOptions;

import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.api.VersionApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link JiraClient} using Vert.x HTTP client.
 * Supports both JIRA REST API v2 and v3 through the {@link #withApiVersion(int)} method.
 *
 * @param <I> The issue type
 */
public class VertxHttpJiraClient<I extends Issue> extends AbstractJiraClient<WebClient, I> {

    private final Vertx vertx;

    /**
     * Creates a new {@link JiraClient} using Vert.x HTTP client with the given issue type.
     *
     * @param vertx     The vert.x instance
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public VertxHttpJiraClient(Vertx vertx, String jiraUrl, Class<I> issueType) {
        super(jiraUrl, issueType);
        this.vertx = vertx;
    }

    /**
     * Creates a new {@link JiraClient} using Vert.x HTTP client with the given issue type.
     *
     * @param jiraUrl   The Jira URL
     * @param issueType The issue class type
     */
    public VertxHttpJiraClient(String jiraUrl, Class<I> issueType) {
        this(Vertx.vertx(), jiraUrl, issueType);
    }

    @Override
    public WebClient newHttpClient() {
        return WebClient.create(vertx, defaultWebClientOptions(proxy));
    }

    @Override
    public IssueApi<I> getIssueApi() {
        return issueApi.get(() -> new VertxHttpIssueApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issueType, apiVersion));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new VertxHttpProjectApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new VertxHttpUserApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new VertxHttpSearchApi<>(newHttpClient(), baseUrl + getBaseApiPath(), authentication, issuesListType, apiVersion));
    }

    @Override
    public VersionApi getVersionApi() {
        return versionApi.get(() -> new VertxHttpVersionApi(newHttpClient(), baseUrl + getBaseApiPath(), authentication, apiVersion));
    }


}
