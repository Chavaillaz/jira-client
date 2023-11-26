package com.chavaillaz.client.jira.vertx;

import static com.chavaillaz.client.common.vertx.VertxUtils.newWebClientOptions;
import static com.chavaillaz.client.jira.JiraConstants.BASE_API;

import com.chavaillaz.client.jira.AbstractJiraClient;
import com.chavaillaz.client.jira.JiraClient;
import com.chavaillaz.client.jira.api.IssueClient;
import com.chavaillaz.client.jira.api.ProjectClient;
import com.chavaillaz.client.jira.api.SearchClient;
import com.chavaillaz.client.jira.api.UserClient;
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
        return WebClient.create(vertx, newWebClientOptions(proxy));
    }

    @Override
    public IssueClient<I> getIssueClient() {
        return issueClient.get(() -> new VertxHttpIssueClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issueType));
    }

    @Override
    public ProjectClient getProjectClient() {
        return projectClient.get(() -> new VertxHttpProjectClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return userClient.get(() -> new VertxHttpUserClient(newHttpClient(), baseUrl + BASE_API, authentication));
    }

    @Override
    public SearchClient<Issues<I>> getSearchClient() {
        return searchClient.get(() -> new VertxHttpSearchClient<>(newHttpClient(), baseUrl + BASE_API, authentication, issuesListType));
    }


}
