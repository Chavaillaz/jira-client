package com.chavaillaz.client.jira;

import com.chavaillaz.client.common.AbstractClient;
import com.chavaillaz.client.common.utility.LazyCachedObject;
import com.chavaillaz.client.jira.api.IssueClient;
import com.chavaillaz.client.jira.api.ProjectClient;
import com.chavaillaz.client.jira.api.SearchClient;
import com.chavaillaz.client.jira.api.UserClient;
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

    protected final Class<I> issueType;
    protected final JavaType issuesListType;

    protected LazyCachedObject<IssueClient<I>> issueClient = new LazyCachedObject<>();
    protected LazyCachedObject<ProjectClient> projectClient = new LazyCachedObject<>();
    protected LazyCachedObject<SearchClient<Issues<I>>> searchClient = new LazyCachedObject<>();
    protected LazyCachedObject<UserClient> userClient = new LazyCachedObject<>();

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
