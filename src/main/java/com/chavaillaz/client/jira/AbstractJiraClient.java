package com.chavaillaz.client.jira;

import com.chavaillaz.client.common.AbstractClient;
import com.chavaillaz.client.common.utility.LazyCachedObject;
import com.chavaillaz.client.jira.JiraConstants.ApiVersion;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.api.VersionApi;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Issues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Abstract class implementing common parts of the {@link JiraClient}.
 *
 * @param <C> HTTP client
 * @param <I> Issue type
 */
public abstract class AbstractJiraClient<C, I extends Issue> extends AbstractClient<C, JiraClient<I>> implements JiraClient<I> {

    protected final LazyCachedObject<IssueApi<I>> issueApi = new LazyCachedObject<>();
    protected final LazyCachedObject<ProjectApi> projectApi = new LazyCachedObject<>();
    protected final LazyCachedObject<SearchApi<Issues<I>>> searchApi = new LazyCachedObject<>();
    protected final LazyCachedObject<UserApi> userApi = new LazyCachedObject<>();
    protected final LazyCachedObject<VersionApi> versionApi = new LazyCachedObject<>();

    protected final Class<I> issueType;
    protected final JavaType issuesListType;

    /**
     * The JIRA REST API version to use.
     * Defaults to v2 for backward compatibility.
     */
    protected ApiVersion apiVersion = ApiVersion.V2;

    /**
     * Creates a new abstract client.
     *
     * @param jiraUrl   The URL of Jira
     * @param issueType The issue class type
     */
    protected AbstractJiraClient(String jiraUrl, Class<I> issueType) {
        super(jiraUrl);

        TypeFactory typeFactory = new ObjectMapper().getTypeFactory();
        this.issueType = issueType;
        this.issuesListType = typeFactory.constructParametricType(Issues.class, issueType);
    }

    /**
     * Sets the JIRA REST API version to use.
     * This method allows you to choose between API v2 and v3.
     * <p>
     * Note: API v3 has breaking changes, particularly in user identification
     * (uses accountId instead of username). See the migration guide for details.
     * <p>
     * Example usage:
     * <pre>{@code
     * JiraClient<Issue> client = JiraClient.apacheClient("https://jira.mycompany.com")
     *     .withApiVersion(3)
     *     .withUserAuthentication("myUsername", "myPassword");
     * }</pre>
     *
     * @param version The API version (2 or 3)
     * @return This client for method chaining
     * @throws IllegalArgumentException if the version is not 2 or 3
     */
    @Override
    public JiraClient<I> withApiVersion(int version) {
        this.apiVersion = ApiVersion.fromInt(version);
        return this;
    }

    /**
     * Gets the base API path for the configured API version.
     * Returns "/rest/api/2/" for v2 or "/rest/api/3/" for v3.
     *
     * @return The base API path
     */
    protected String getBaseApiPath() {
        return JiraConstants.getBaseApiPath(apiVersion);
    }

    @Override
    public int getApiVersion() {
        return apiVersion.getVersionNumber();
    }

    @Override
    public String getFullBaseUrl() {
        return baseUrl + getBaseApiPath();
    }

}
