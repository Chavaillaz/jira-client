package com.chavaillaz.client.jira.okhttp;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.domain.Filter;
import com.chavaillaz.client.jira.domain.Filters;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Query;
import com.fasterxml.jackson.databind.JavaType;
import okhttp3.OkHttpClient;

public class OkHttpSearchApi<T extends List<? extends Issue>> extends AbstractOkHttpClient implements SearchApi<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchApi} using OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     * @param issuesListType The issues list class type
     */
    public OkHttpSearchApi(OkHttpClient client, String baseUrl, Authentication authentication, JavaType issuesListType) {
        super(client, baseUrl, authentication);
        this.issuesListType = issuesListType;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, List<String> expand) {
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
