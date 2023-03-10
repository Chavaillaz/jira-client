package com.chavaillaz.jira.client;

import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;

public interface JiraClient<I extends Issue> {

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

    /**
     * Sets the proxy to use for all requests to the Jira API.
     *
     * @param host The proxy host
     * @param port The proxy port
     * @return The current client instance
     */
    JiraClient<I> withProxy(String host, Integer port);

    /**
     * Sets the proxy to use for all requests to the Jira API.
     *
     * @param url The proxy URL
     * @return The current client instance
     */
    JiraClient<I> withProxy(String url);

    /**
     * Sets the credentials to use for all requests to the Jira API.
     *
     * @param username The username
     * @param password The password
     * @return The current client instance
     */
    JiraClient<I> withAuthentication(String username, String password);

    /**
     * Sets the credentials to use for all requests to the Jira API.
     *
     * @param token The personal access token
     * @return The current client instance
     */
    JiraClient<I> withAuthentication(String token);

}
