package com.chavaillaz.client.jira.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.delete;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.put;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jira.JiraAuthentication;
import com.chavaillaz.client.jira.api.SearchClient;
import com.chavaillaz.client.jira.domain.Filter;
import com.chavaillaz.client.jira.domain.Filters;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.Query;
import com.fasterxml.jackson.databind.JavaType;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

public class ApacheHttpSearchClient<T extends List<? extends Issue>> extends AbstractApacheHttpClient implements SearchClient<T> {

    protected final JavaType issuesListType;

    /**
     * Creates a new {@link SearchClient} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     * @param issuesListType The issues list class type
     */
    public ApacheHttpSearchClient(CloseableHttpAsyncClient client, String baseUrl, JiraAuthentication authentication, JavaType issuesListType) {
        super(client, baseUrl, authentication);
        this.issuesListType = issuesListType;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, List<String> expand) {
        return sendAsync(requestBuilder(post(), URL_SEARCH)
                .setBody(serialize(Query.from(jql, startAt, maxResults, expand)), APPLICATION_JSON), issuesListType);
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
