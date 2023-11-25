package com.chavaillaz.jira.client;

import static com.chavaillaz.client.Authentication.AuthenticationType.PASSWORD;
import static com.chavaillaz.client.Authentication.AuthenticationType.TOKEN;

import com.chavaillaz.client.AbstractClient;
import com.chavaillaz.client.utility.LazyCachedObject;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Abstract class implementing common parts of the {@link JiraClient}.
 *
 * @param <C> HTTP client
 * @param <I> Issue type
 */
public abstract class AbstractJiraClient<C, I extends Issue> extends AbstractClient<C, JiraAuthentication, JiraClient<I>> implements JiraClient<I> {

    public static final String BASE_API = "/rest/api/2/";
    protected final Class<I> issueType;
    protected final JavaType issuesListType;

    protected LazyCachedObject<IssueClient<I>> cacheIssueClient = new LazyCachedObject<>();
    protected LazyCachedObject<ProjectClient> cacheProjectClient = new LazyCachedObject<>();
    protected LazyCachedObject<SearchClient<Issues<I>>> cacheSearchClient = new LazyCachedObject<>();
    protected LazyCachedObject<UserClient> cacheUserClient = new LazyCachedObject<>();

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


    @Override
    public JiraClient<I> withTokenAuthentication(String username, String token) {
        this.authentication = new JiraAuthentication(TOKEN, username, token);
        return this;
    }

    @Override
    public JiraClient<I> withUserAuthentication(String username, String password) {
        this.authentication = new JiraAuthentication(PASSWORD, username, password);
        return this;
    }

    @Override
    public JiraClient<I> withAnonymousAuthentication() {
        this.authentication = new JiraAuthentication();
        return this;
    }

}
