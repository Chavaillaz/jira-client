package com.chavaillaz.jira.client.apache;

import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.domain.Filter;
import com.chavaillaz.jira.domain.Filters;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.Query;
import com.fasterxml.jackson.databind.JavaType;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.*;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

public class ApacheHttpSearchClient<T extends List<? extends Issue>> extends AbstractApacheHttpClient implements SearchClient<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchClient} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     * @param issuesListType The issues list class type
     */
    public ApacheHttpSearchClient(CloseableHttpAsyncClient client, String baseUrl, String authentication, JavaType issuesListType) {
        super(client, baseUrl, authentication);
        this.issuesListType = issuesListType;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, String expand) {
        return sendAsyncReturnDomain(requestBuilder(post(), URL_SEARCH)
                .setBody(serialize(Query.from(jql, startAt, maxResults, expand)), APPLICATION_JSON), issuesListType);
    }

    @Override
    public CompletableFuture<Filter> addFilter(Filter filter) {
        return sendAsyncReturnDomain(requestBuilder(post(), URL_FILTERS)
                .setBody(serialize(filter), APPLICATION_JSON), Filter.class);
    }

    @Override
    public CompletableFuture<Filter> getFilter(String id) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_FILTER, id), Filter.class);
    }

    @Override
    public CompletableFuture<Filters> getFavoriteFilters() {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_FILTER_FAVOURITE), Filters.class);
    }

    @Override
    public CompletableFuture<Filter> updateFilter(String id, Filter filter) {
        return sendAsyncReturnDomain(requestBuilder(put(), URL_FILTER, id)
                .setBody(serialize(filter), APPLICATION_JSON), Filter.class);
    }

    @Override
    public CompletableFuture<Void> deleteFilter(String id) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_FILTER, id));
    }

}
