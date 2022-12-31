package com.chavaillaz.jira.client;

import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;

import java.util.List;

public interface JiraClient {

    /**
     * Gets the issue client with the default issue type.
     *
     * @return The issue client
     */
    default IssueClient<Issue> getIssueClient() {
        return getIssueClient(Issue.class);
    }

    /**
     * Gets the issue client with the given issue type.
     * It should be used in case of an extension of the {@link Issue} class.
     *
     * @param issueClass The issue type to use instead of the default one
     * @param <T>        The issue type
     * @return The issue client
     */
    <T extends Issue> IssueClient<T> getIssueClient(Class<T> issueClass);

    /**
     * Gets the search client with the default issue list type.
     *
     * @return The search client
     */
    default SearchClient<Issues> getSearchClient() {
        return getSearchClient(Issues.class);
    }

    /**
     * Gets the search client with the given issue list type.
     * It should be used in case of an extension of the {@link Issues} class.
     *
     * @param issuesClass The issues list type to use instead of the default one
     * @param <T>         The issue list type
     * @return The search client
     */
    <T extends List<? extends Issue>> SearchClient<T> getSearchClient(Class<T> issuesClass);

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
    JiraClient withProxy(String host, Integer port);

    /**
     * Sets the proxy to use for all requests to the Jira API.
     *
     * @param url The proxy URL
     * @return The current client instance
     */
    JiraClient withProxy(String url);

    /**
     * Sets the credentials to use for all requests to the Jira API.
     *
     * @param username The username
     * @param password The password
     * @return The current client instance
     */
    JiraClient withAuthentication(String username, String password);

    /**
     * Sets the credentials to use for all requests to the Jira API.
     *
     * @param token The personal access token
     * @return The current client instance
     */
    JiraClient withAuthentication(String token);

}
