package com.chavaillaz.jira.client.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.delete;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.put;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.domain.Filter;
import com.chavaillaz.jira.domain.Filters;
import com.chavaillaz.jira.domain.Issues;
import com.chavaillaz.jira.domain.Query;

public class ApacheHttpSearchClient<T extends Issues> extends AbstractApacheHttpClient implements SearchClient<T> {

    protected final Class<T> issueClass;

    /**
     * Creates a new {@link SearchClient} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     * @param issueListClass The issue class type in case of an extension
     */
    public ApacheHttpSearchClient(CloseableHttpAsyncClient client, String baseUrl, String authentication, Class<T> issueListClass) {
        super(client, baseUrl, authentication);
        this.issueClass = issueListClass;
    }

    @Override
    public CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, String expand) {
        return sendAsyncReturnDomain(requestBuilder(post(), URL_SEARCH)
                .setBody(serialize(Query.from(jql, startAt, maxResults, expand)), APPLICATION_JSON), issueClass);
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
