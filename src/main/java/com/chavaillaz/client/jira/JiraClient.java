package com.chavaillaz.client.jira;

import static io.vertx.core.Vertx.vertx;

import com.chavaillaz.client.common.Client;
import com.chavaillaz.client.jira.apache.ApacheHttpJiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import com.chavaillaz.client.jira.java.JavaHttpJiraClient;
import com.chavaillaz.client.jira.okhttp.OkHttpJiraClient;
import com.chavaillaz.client.jira.vertx.VertxHttpJiraClient;

public interface JiraClient<I extends Issue> extends Client<JiraClient<I>> {

    /**
     * Creates a default {@link JiraClient} with Apache HTTP client.
     *
     * @param jiraUrl The Jira URL
     */
    static ApacheHttpJiraClient<Issue> apacheClient(String jiraUrl) {
        return new ApacheHttpJiraClient<>(jiraUrl, Issue.class);
    }

    /**
     * Creates a default {@link JiraClient} with Java HTTP client.
     *
     * @param jiraUrl The Jira URL
     */
    static JavaHttpJiraClient<Issue> javaClient(String jiraUrl) {
        return new JavaHttpJiraClient<>(jiraUrl, Issue.class);
    }

    /**
     * Creates a default {@link JiraClient} with OkHttp client.
     *
     * @param jiraUrl The Jira URL
     */
    static OkHttpJiraClient<Issue> okHttpClient(String jiraUrl) {
        return new OkHttpJiraClient<>(jiraUrl, Issue.class);
    }

    /**
     * Creates a default {@link JiraClient} with Vert.x client.
     *
     * @param jiraUrl The Jira URL
     */
    static VertxHttpJiraClient<Issue> vertxClient(String jiraUrl) {
        return new VertxHttpJiraClient<>(vertx(), jiraUrl, Issue.class);
    }

    /**
     * Gets the issue API with the default issue type.
     *
     * @return The issue client
     */
    IssueApi<I> getIssueApi();

    /**
     * Gets the search API with the default issue list type.
     *
     * @return The search client
     */
    SearchApi<Issues<I>> getSearchApi();

    /**
     * Gets the project API.
     *
     * @return The project client
     */
    ProjectApi getProjectApi();

    /**
     * Gets the project API.
     *
     * @return The project client
     */
    UserApi getUserApi();

}
