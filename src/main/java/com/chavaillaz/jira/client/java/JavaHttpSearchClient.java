package com.chavaillaz.jira.client.java;

import java.net.http.HttpClient;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.domain.Filter;
import com.chavaillaz.jira.domain.Filters;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Query;
import com.fasterxml.jackson.databind.JavaType;

public class JavaHttpSearchClient<T extends List<? extends Issue>> extends AbstractJavaHttpClient implements SearchClient<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchClient} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     * @param issuesListType The issues list class type
     */
    public JavaHttpSearchClient(HttpClient client, String baseUrl, String authentication, JavaType issuesListType) {
        super(client, baseUrl, authentication);
        this.issuesListType = issuesListType;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, String expand) {
        return sendAsync(requestBuilder(URL_SEARCH).POST(body(Query.from(jql, startAt, maxResults, expand))), issuesListType);
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
