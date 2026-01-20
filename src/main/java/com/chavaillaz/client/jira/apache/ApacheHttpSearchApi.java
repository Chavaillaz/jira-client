package com.chavaillaz.client.jira.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.delete;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.put;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

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
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

public class ApacheHttpSearchApi<T extends List<? extends Issue>> extends AbstractApacheHttpClient implements SearchApi<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchApi} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira (including API version path)
     * @param authentication The authentication information
     * @param issuesListType The issues list class type
     * @param apiVersion     The API version to use
     */
    public ApacheHttpSearchApi(CloseableHttpAsyncClient client, String baseUrl, Authentication authentication, JavaType issuesListType, ApiVersion apiVersion) {
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

        return sendAsync(requestBuilder(post(), SearchApi.getSearchUrl(apiVersion))
                .setBody(serialize(requestBody), APPLICATION_JSON), issuesListType);
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, String nextPageToken, Integer maxResults, Set<IssueExpand> expand, Set<String> fields) {
        // This method is specifically for v3 pagination
        Object requestBody = SearchAndReconcileRequest.from(jql, nextPageToken, maxResults, expand, fields);
        return sendAsync(requestBuilder(post(), SearchApi.getSearchUrl(apiVersion))
                .setBody(serialize(requestBody), APPLICATION_JSON), issuesListType);
    }

    @Override
    public CompletableFuture<Filter> addFilter(Filter filter) {
        return sendAsync(requestBuilder(post(), URL_FILTERS)
                .setBody(serialize(filter), APPLICATION_JSON), Filter.class);
    }

    @Override
    public CompletableFuture<Filter> getFilter(String id) {
        return sendAsync(requestBuilder(get(), URL_FILTER, id), Filter.class);
    }

    @Override
    public CompletableFuture<Filters> getFavoriteFilters() {
        return sendAsync(requestBuilder(get(), URL_FILTER_FAVOURITE), Filters.class);
    }

    @Override
    public CompletableFuture<Filter> updateFilter(String id, Filter filter) {
        return sendAsync(requestBuilder(put(), URL_FILTER, id)
                .setBody(serialize(filter), APPLICATION_JSON), Filter.class);
    }

    @Override
    public CompletableFuture<Void> deleteFilter(String id) {
        return sendAsync(requestBuilder(delete(), URL_FILTER, id), Void.class);
    }

}
