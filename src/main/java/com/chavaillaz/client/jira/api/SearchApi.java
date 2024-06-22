package com.chavaillaz.client.jira.api;

import static com.chavaillaz.client.jira.api.IssueApi.ALL_FIELDS;
import static java.util.Collections.emptySet;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.join;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jira.api.expand.IssueExpand;
import com.chavaillaz.client.jira.domain.Filter;
import com.chavaillaz.client.jira.domain.Filters;
import com.chavaillaz.client.jira.domain.Issue;

public interface SearchApi<T extends List<? extends Issue>> extends AutoCloseable {

    String URL_SEARCH = "search";
    String URL_FILTERS = "filter";
    String URL_FILTER = "filter/{0}";
    String URL_FILTER_FAVOURITE = "filter/favourite";

    /**
     * Gets the issues corresponding to the given keys.
     *
     * @param issuesKey The list of issues key
     * @return The corresponding list of issues
     */
    default CompletableFuture<T> getIssues(Set<String> issuesKey) {
        return getIssues(issuesKey, ALL_FIELDS);
    }

    /**
     * Gets the issues corresponding to the given keys.
     *
     * @param issuesKey The list of issues key
     * @param fields    The list of fields to retrieve for those issues
     * @return The corresponding list of issues
     */
    default CompletableFuture<T> getIssues(Set<String> issuesKey, Set<String> fields) {
        // Sending an empty list would make the query fails so the whole query is replaced by an empty one
        // Note that an empty list cannot be returned directly as we don't know the class of the generic T
        String jql = (issuesKey == null || issuesKey.isEmpty()) ? EMPTY : "issue in (" + join(issuesKey, ",") + ")";
        Integer size = issuesKey == null ? 0 : issuesKey.size();
        return searchIssues(jql, 0, size, emptySet(), fields);
    }

    /**
     * Performs a search for issues.
     * The page offset is 0 and the number of results returned per page is 50.
     * No parameter is expanded and all navigable fields in the issues are returned.
     *
     * @param jql The query in Jira Query Language
     * @return A {@link CompletableFuture} with the issues found
     */
    default CompletableFuture<T> searchIssues(String jql) {
        return searchIssues(jql, 0, 50);
    }

    /**
     * Performs a search for issues.
     * No parameter is expanded and all fields in the issues are returned.
     *
     * @param jql        The query in Jira Query Language
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @return A {@link CompletableFuture} with the issues found
     */
    default CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults) {
        return searchIssues(jql, startAt, maxResults, emptySet());
    }

    /**
     * Performs a search for issues.
     * All navigable fields in the issues are returned.
     *
     * @param jql        The query in Jira Query Language
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @param expand     The list of parameters to expand
     * @return A {@link CompletableFuture} with the issues found
     */
    default CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, Set<IssueExpand> expand) {
        return searchIssues(jql, startAt, maxResults, expand, ALL_FIELDS);
    }

    /**
     * Performs a search for issues.
     *
     * @param jql        The query in Jira Query Language
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @param expand     The list of parameters to expand
     * @param fields     The list of fields to be present in the issues retrieved
     * @return A {@link CompletableFuture} with the issues found
     */
    CompletableFuture<T> searchIssues(String jql, Integer startAt, Integer maxResults, Set<IssueExpand> expand, Set<String> fields);

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
     * Gets a filter.
     * Note that if the filter does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param id The filter identifier
     * @return A {@link CompletableFuture} with the corresponding optional filter
     */
    default CompletableFuture<Optional<Filter>> getFilterOptional(String id) {
        return getFilter(id)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

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