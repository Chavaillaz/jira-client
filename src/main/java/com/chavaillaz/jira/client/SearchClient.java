package com.chavaillaz.jira.client;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.domain.Filter;
import com.chavaillaz.jira.domain.Filters;
import com.chavaillaz.jira.domain.Issue;

public interface SearchClient<T extends List<? extends Issue>> extends AutoCloseable {

    String URL_SEARCH = "search";
    String URL_FILTERS = "filter";
    String URL_FILTER = "filter/{0}";
    String URL_FILTER_FAVOURITE = "filter/favourite";

    /**
     * Performs a search for issues.
     * This uses a page offset of 0 and a number of results par page of 50.
     *
     * @param jql The query in Jira Query Language
     * @return A {@link CompletableFuture} with the issues found
     */
    default CompletableFuture<T> searchIssues(String jql) {
        return searchIssues(jql, 0, 50);
    }

    /**
     * Performs a search for issues.
     *
     * @param jql        The query in Jira Query Language
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @return A {@link CompletableFuture} with the issues found
     */
    default CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults) {
        return searchIssues(jql, startAt, maxResults, emptyList());
    }

    /**
     * Performs a search for issues.
     *
     * @param jql        The query in Jira Query Language
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @param expand     The list of parameters to expand
     * @return A {@link CompletableFuture} with the issues found
     */
    CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, List<String> expand);

    /**
     * Creates a new search filter for the logged-in user.
     *
     * @param filter The filter to create
     * @return A {@link CompletableFuture} with the filter created
     */
    CompletableFuture<Filter> addFilter(Filter filter);

    /**
     * Gets a filter.
     *
     * @param id The filter identifier
     * @return A {@link CompletableFuture} with the filter found
     */
    CompletableFuture<Filter> getFilter(String id);

    /**
     * Gets all favorite filters of the logged-in user.
     *
     * @return A {@link CompletableFuture} with the filters found
     */
    CompletableFuture<Filters> getFavoriteFilters();

    /**
     * Updates a filter.
     *
     * @param id     The filter identifier
     * @param filter The filter to update
     * @return A {@link CompletableFuture} with the updated filter
     */
    CompletableFuture<Filter> updateFilter(String id, Filter filter);

    /**
     * Deletes a filter.
     *
     * @param id The filter identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteFilter(String id);

}