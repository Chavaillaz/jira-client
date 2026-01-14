package com.chavaillaz.client.jira.java;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.JiraConstants.ApiVersion;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.expand.IssueExpand;
import com.chavaillaz.client.jira.domain.Filter;
import com.chavaillaz.client.jira.domain.Filters;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Query;
import com.chavaillaz.client.jira.domain.SearchAndReconcileRequest;
import com.fasterxml.jackson.databind.JavaType;

public class JavaHttpSearchApi<T extends List<? extends Issue>> extends AbstractJavaHttpClient implements SearchApi<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchApi} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira (including API version path)
     * @param authentication The authentication information
     * @param issuesListType The issues list class type
     * @param apiVersion     The API version to use
     */
    public JavaHttpSearchApi(HttpClient client, String baseUrl, Authentication authentication, JavaType issuesListType, ApiVersion apiVersion) {
        super(client, baseUrl, authentication, apiVersion);
        this.issuesListType = issuesListType;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, Set<IssueExpand> expand, Set<String> fields) {
        // For v3, use SearchAndReconcileRequest; for v2, use Query
        Object requestBody;
        if (apiVersion == ApiVersion.V3) {
            requestBody = SearchAndReconcileRequest.from(jql, null, maxResults, expand, fields);
        } else {
            requestBody = Query.from(jql, startAt, maxResults, expand, fields);
        }

        return sendAsync(requestBuilder(SearchApi.getSearchUrl(apiVersion)).POST(body(requestBody)), issuesListType);
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, String nextPageToken, Integer maxResults, Set<IssueExpand> expand, Set<String> fields) {
        // This method is specifically for v3 pagination
        Object requestBody = SearchAndReconcileRequest.from(jql, nextPageToken, maxResults, expand, fields);
        return sendAsync(requestBuilder(SearchApi.getSearchUrl(apiVersion)).POST(body(requestBody)), issuesListType);
    }

    @Override
    public CompletableFuture<Filter> addFilter(Filter filter) {
        return sendAsync(requestBuilder(URL_FILTERS).POST(body(filter)), Filter.class);
    }

    @Override
    public CompletableFuture<Filter> getFilter(String id) {
        return sendAsync(requestBuilder(URL_FILTER, id).GET(), Filter.class);
    }

    @Override
    public CompletableFuture<Filters> getFavoriteFilters() {
        return sendAsync(requestBuilder(URL_FILTER_FAVOURITE).GET(), Filters.class);
    }

    @Override
    public CompletableFuture<Filter> updateFilter(String id, Filter filter) {
        return sendAsync(requestBuilder(URL_FILTER, id).PUT(body(filter)), Filter.class);
    }

    @Override
    public CompletableFuture<Void> deleteFilter(String id) {
        return sendAsync(requestBuilder(URL_FILTER, id).DELETE(), Void.class);
    }

}
