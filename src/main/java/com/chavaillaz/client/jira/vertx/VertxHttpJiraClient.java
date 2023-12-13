package com.chavaillaz.client.jira.vertx;

import static com.chavaillaz.client.common.vertx.VertxUtils.defaultWebClientOptions;
import static com.chavaillaz.client.jira.JiraConstants.BASE_API;

import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

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

    @Override
    public WebClient newHttpClient() {
        return WebClient.create(vertx, defaultWebClientOptions(proxy));
    }

    @Override
    public IssueApi<I> getIssueApi() {
        return issueApi.get(() -> new VertxHttpIssueApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectApi getProjectApi() {
        return projectApi.get(() -> new VertxHttpProjectApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new VertxHttpUserApi(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchApi<Issues<I>> getSearchApi() {
        return searchApi.get(() -> new VertxHttpSearchApi<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }


}
