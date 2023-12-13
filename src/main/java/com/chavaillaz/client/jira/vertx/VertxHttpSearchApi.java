package com.chavaillaz.client.jira.vertx;

import static io.vertx.core.http.HttpMethod.DELETE;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;
import static io.vertx.core.http.HttpMethod.PUT;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.expand.IssueExpand;
import com.chavaillaz.client.jira.domain.Filter;
import com.chavaillaz.client.jira.domain.Filters;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Query;
import com.fasterxml.jackson.databind.JavaType;
import io.vertx.ext.web.client.WebClient;

public class VertxHttpSearchApi<T extends List<? extends Issue>> extends AbstractVertxHttpClient implements SearchApi<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchApi} using Vert.x client.
     *
     * @param client         The Vert.x client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     * @param issuesListType The issues list class type
     */
    public VertxHttpSearchApi(WebClient client, String baseUrl, Authentication authentication, JavaType issuesListType) {
        super(client, baseUrl, authentication);
        this.issuesListType = issuesListType;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, Set<IssueExpand> expand, Set<String> fields) {
        return handleAsync(requestBuilder(POST, URL_SEARCH).sendBuffer(body(Query.from(jql, startAt, maxResults, expand, fields))), issuesListType);
    }

    @Override
    public CompletableFuture<Filter> addFilter(Filter filter) {
        return handleAsync(requestBuilder(POST, URL_FILTERS).sendBuffer(body(filter)), Filter.class);
    }

    @Override
    public CompletableFuture<Filter> getFilter(String id) {
        return handleAsync(requestBuilder(GET, URL_FILTER, id).send(), Filter.class);
    }

    @Override
    public CompletableFuture<Filters> getFavoriteFilters() {
        return handleAsync(requestBuilder(GET, URL_FILTER_FAVOURITE).send(), Filters.class);
    }

    @Override
    public CompletableFuture<Filter> updateFilter(String id, Filter filter) {
        return handleAsync(requestBuilder(PUT, URL_FILTER, id).sendBuffer(body(filter)), Filter.class);
    }

    @Override
    public CompletableFuture<Void> deleteFilter(String id) {
        return handleAsync(requestBuilder(DELETE, URL_FILTER, id).send(), Void.class);
    }

}
