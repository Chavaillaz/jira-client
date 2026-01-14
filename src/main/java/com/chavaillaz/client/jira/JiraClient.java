package com.chavaillaz.client.jira;

import com.chavaillaz.client.common.Client;
import com.chavaillaz.client.jira.apache.ApacheHttpJiraClient;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.api.VersionApi;
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
        return new VertxHttpJiraClient<>(jiraUrl, Issue.class);
    }

    /**
     * Sets the JIRA REST API version to use for this client.
     * <p>
     * By default, the client uses API v2 for backward compatibility.
     * You can switch to API v3 by calling this method with version 3.
     * <p>
     * <b>Important:</b> API v3 has breaking changes, particularly in user identification
     * (uses accountId instead of username). Ensure you understand the differences before
     * switching to v3. See the API v3 migration guide for details.
     * <p>
     * Example usage:
     * <pre>{@code
     * // Use API v2 (default)
     * JiraClient<Issue> clientV2 = JiraClient.apacheClient("https://jira.mycompany.com")
     *     .withUserAuthentication("myUsername", "myPassword");
     *
     * // Use API v3
     * JiraClient<Issue> clientV3 = JiraClient.apacheClient("https://jira.mycompany.com")
     *     .withApiVersion(3)
     *     .withUserAuthentication("myUsername", "myPassword");
     * }</pre>
     *
     * @param version The API version to use (2 or 3)
     * @return This client instance for method chaining
     * @throws IllegalArgumentException if version is not 2 or 3
     * @since 3.0
     */
    JiraClient<I> withApiVersion(int version);

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

    /**
     * Gets the version API.
     *
     * @return The version client
     */
    VersionApi getVersionApi();

    /**
     * Gets the current API version being used by this client.
     *
     * @return The API version (2 or 3)
     * @since 3.0
     */
    int getApiVersion();

    /**
     * Gets the full base URL including the API version path.
     * This can be useful for debugging to see which API endpoint is being used.
     * <p>
     * For example:
     * <ul>
     *   <li>API v2: https://jira.mycompany.com/rest/api/2/</li>
     *   <li>API v3: https://jira.mycompany.com/rest/api/3/</li>
     * </ul>
     *
     * @return The full base URL with API version path
     * @since 3.0
     */
    String getFullBaseUrl();

}
