package com.chavaillaz.jira.client;

import java.util.Base64;

import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;
import com.chavaillaz.jira.utils.ProxyConfiguration;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Abstract class implementing common parts of the {@link JiraClient}.
 *
 * @param <C> HTTP client
 * @param <I> Issue type
 */
public abstract class AbstractJiraClient<C, I extends Issue> implements JiraClient<I> {

    public static final String BASE_API = "/rest/api/2/";

    protected final Class<I> issueType;
    protected final JavaType issuesListType;
    protected final String jiraUrl;
    protected String authentication;
    protected IssueClient<I> cacheIssueClient;
    protected ProjectClient cacheProjectClient;
    protected SearchClient<Issues<I>> cacheSearchClient;
    protected UserClient cacheUserClient;
    protected ProxyConfiguration proxy;

    /**
     * Creates a new abstract client.
     *
     * @param jiraUrl   The URL of Jira
     * @param issueType The issue class type
     */
    protected AbstractJiraClient(String jiraUrl, Class<I> issueType) {
        this.jiraUrl = jiraUrl;

        TypeFactory typeFactory = new ObjectMapper().getTypeFactory();
        this.issueType = issueType;
        this.issuesListType = typeFactory.constructParametricType(Issues.class, issueType);
    }

    /**
     * Creates a new HTTP client that will be used to communicate with Jira REST endpoints.
     *
     * @return The HTTP client
     */
    protected abstract C newHttpClient();

    @Override
    public JiraClient<I> withProxy(String host, Integer port) {
        this.proxy = ProxyConfiguration.from(host, port);
        return this;
    }

    @Override
    public JiraClient<I> withProxy(String url) {
        this.proxy = ProxyConfiguration.from(url);
        return this;
    }

    @Override
    public JiraClient<I> withAuthentication(String token) {
        this.authentication = "Bearer " + token;
        return this;
    }

    @Override
    public JiraClient<I> withAuthentication(String username, String password) {
        this.authentication = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        return this;
    }

}
