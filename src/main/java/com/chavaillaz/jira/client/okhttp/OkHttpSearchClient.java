package com.chavaillaz.jira.client.okhttp;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.domain.Filter;
import com.chavaillaz.jira.domain.Filters;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Query;
import com.fasterxml.jackson.databind.JavaType;
import okhttp3.OkHttpClient;

public class OkHttpSearchClient<T extends List<? extends Issue>> extends AbstractOkHttpClient implements SearchClient<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchClient} using OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     * @param issuesListType The issues list class type
     */
    public OkHttpSearchClient(OkHttpClient client, String baseUrl, String authentication, JavaType issuesListType) {
        super(client, baseUrl, authentication);
        this.issuesListType = issuesListType;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, String expand) {
        return sendAsync(requestBuilder(URL_SEARCH).post(body(Query.from(jql, startAt, maxResults, expand))), issuesListType);
    }

    @Override
    public CompletableFuture<Filter> addFilter(Filter filter) {
        return sendAsync(requestBuilder(URL_FILTERS).post(body(filter)), Filter.class);
    }

    @Override
    public CompletableFuture<Filter> getFilter(String id) {
        return sendAsync(requestBuilder(URL_FILTER, id).get(), Filter.class);
    }

    @Override
    public CompletableFuture<Filters> getFavoriteFilters() {
        return sendAsync(requestBuilder(URL_FILTER_FAVOURITE).get(), Filters.class);
    }

    @Override
    public CompletableFuture<Filter> updateFilter(String id, Filter filter) {
        return sendAsync(requestBuilder(URL_FILTER, id).put(body(filter)), Filter.class);
    }

    @Override
    public CompletableFuture<Void> deleteFilter(String id) {
        return sendAsync(requestBuilder(URL_FILTER, id).delete(), Void.class);
    }

}
