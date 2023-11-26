package com.chavaillaz.client.jira;

import com.chavaillaz.client.common.Client;
import com.chavaillaz.client.jira.apache.ApacheHttpJiraClient;
import com.chavaillaz.client.jira.api.IssueClient;
import com.chavaillaz.client.jira.api.ProjectClient;
import com.chavaillaz.client.jira.api.SearchClient;
import com.chavaillaz.client.jira.api.UserClient;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import com.chavaillaz.client.jira.java.JavaHttpJiraClient;
import com.chavaillaz.client.jira.okhttp.OkHttpJiraClient;

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
     * Gets the issue client with the default issue type.
     *
     * @return The issue client
     */
    IssueClient<I> getIssueClient();

    /**
     * Gets the search client with the default issue list type.
     *
     * @return The search client
     */
    SearchClient<Issues<I>> getSearchClient();

    /**
     * Gets the project client.
     *
     * @return The project client
     */
    ProjectClient getProjectClient();

    /**
     * Gets the project client.
     *
     * @return The project client
     */
    UserClient getUserClient();

}
