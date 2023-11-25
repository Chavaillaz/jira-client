package com.chavaillaz.jira.client;

import com.chavaillaz.client.Client;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;

public interface JiraClient<I extends Issue> extends Client<JiraClient<I>> {

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
