package com.chavaillaz.jira.client.apache;

import java.util.Optional;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.core5.http.HttpHost;

import com.chavaillaz.jira.client.AbstractJiraClient;
import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.client.JiraClient;
import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Issues;

public class ApacheHttpJiraClient extends AbstractJiraClient<CloseableHttpAsyncClient> {

    /**
     * Creates a new {@link JiraClient} with Apache HTTP client.
     *
     * @param jiraUrl The Jira URL
     */
    public ApacheHttpJiraClient(String jiraUrl) {
        super(jiraUrl);
    }

    @Override
    public CloseableHttpAsyncClient newHttpClient() {
        return HttpAsyncClientBuilder.create()
                .useSystemProperties()
                .setProxy(Optional.ofNullable(proxy)
                        .map(config -> new HttpHost(config.getScheme(), config.getHost(), config.getPort()))
                        .orElse(null))
                .build();
    }

    @Override
    public <T extends Issue> IssueClient<T> getIssueClient(Class<T> issueClass) {
        return new ApacheHttpIssueClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issueClass);
    }

    @Override
    public ProjectClient getProjectClient() {
        return new ApacheHttpProjectClient(newHttpClient(), jiraUrl + BASE_API, authentication);
    }

    @Override
    public UserClient getUserClient() {
        return new ApacheHttpUserClient(newHttpClient(), jiraUrl + BASE_API, authentication);
    }

    @Override
    public <T extends Issues> SearchClient<T> getSearchClient(Class<T> issuesClass) {
        return new ApacheHttpSearchClient<>(newHttpClient(), jiraUrl + BASE_API, authentication, issuesClass);
    }

}
