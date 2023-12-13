package com.chavaillaz.client.jira;

import com.chavaillaz.client.common.AbstractClient;
import com.chavaillaz.client.common.utility.LazyCachedObject;
import com.chavaillaz.client.jira.api.IssueApi;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
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

    protected final Class<I> issueType;
    protected final JavaType issuesListType;

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

}
