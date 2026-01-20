package com.chavaillaz.client.jira.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Helper class for paginating through search results.
 * This utility provides convenient methods to fetch all pages of results automatically
 * and supports both v2 (offset-based) and v3 (token-based) pagination.
 */
public class PaginationHelper {

    /**
     * Fetches all pages of issues for a given JQL query using v3 token-based pagination.
     * This method automatically handles the pagination by checking the {@code isLast} field
     * and using the {@code nextPageToken} to fetch subsequent pages.
     *
     * <p><b>For v3 API:</b></p>
     * <pre>{@code
     * CompletableFuture<List<Issue>> allIssues = PaginationHelper.fetchAllPages(
     *     "project = TEST",
     *     token -> jira.getSearchApi().searchIssues("project = TEST", token)
     * );
     * }</pre>
     *
     * @param <T>             The issues list type (extends List&lt;? extends Issue&gt;)
     * @param jql             The JQL query string (used for documentation/debugging)
     * @param searchFunction  A function that takes a nextPageToken (can be null for first page)
     *                        and returns a CompletableFuture with the search results
     * @return A CompletableFuture containing all issues from all pages
     */
    public static <T extends Issue> CompletableFuture<List<T>> fetchAllPages(
            String jql,
            Function<String, CompletableFuture<Issues<T>>> searchFunction) {

        List<T> allIssues = new ArrayList<>();
        return fetchPagesRecursivelyV3(null, searchFunction, allIssues);
    }

    /**
     * Fetches all pages of issues for a given JQL query using v2 offset-based pagination.
     * This method automatically handles the pagination by using {@code startAt}, {@code maxResults},
     * and {@code total} fields.
     *
     * <p><b>For v2 API:</b></p>
     * <pre>{@code
     * CompletableFuture<List<Issue>> allIssues = PaginationHelper.fetchAllPagesV2(
     *     "project = TEST",
     *     50, // page size
     *     (startAt, maxResults) -> jira.getSearchApi().searchIssues("project = TEST", startAt, maxResults)
     * );
     * }</pre>
     *
     * @param <T>             The issues list type (extends List&lt;? extends Issue&gt;)
     * @param jql             The JQL query string (used for documentation/debugging)
     * @param maxResults      The number of results per page
     * @param searchFunction  A function that takes startAt and maxResults parameters
     *                        and returns a CompletableFuture with the search results
     * @return A CompletableFuture containing all issues from all pages
     */
    public static <T extends Issue> CompletableFuture<List<T>> fetchAllPagesV2(
            String jql,
            Integer maxResults,
            BiFunction<Integer, Integer, CompletableFuture<Issues<T>>> searchFunction) {

        List<T> allIssues = new ArrayList<>();
        return fetchPagesRecursivelyV2(0, maxResults, searchFunction, allIssues, null);
    }

    /**
     * Recursively fetches pages of results until the last page is reached (v3 pagination).
     *
     * @param <T>             The issues list type
     * @param nextPageToken   The token for the next page (null for first page)
     * @param searchFunction  The function to call to fetch a page
     * @param accumulator     The list accumulating all issues
     * @return A CompletableFuture containing all accumulated issues
     */
    private static <T extends Issue> CompletableFuture<List<T>> fetchPagesRecursivelyV3(
            String nextPageToken,
            Function<String, CompletableFuture<Issues<T>>> searchFunction,
            List<T> accumulator) {

        return searchFunction.apply(nextPageToken).thenCompose(issues -> {
            // Add current page results to accumulator
            accumulator.addAll(issues);

            // Check if there are more pages (v3 style)
            Boolean isLast = issues.getIsLast();
            String nextToken = issues.getNextPageToken();

            if (isLast == null || isLast || nextToken == null) {
                // This is the last page, return accumulated results
                return CompletableFuture.completedFuture(accumulator);
            } else {
                // Fetch next page recursively
                return fetchPagesRecursivelyV3(nextToken, searchFunction, accumulator);
            }
        });
    }

    /**
     * Recursively fetches pages of results until the last page is reached (v2 pagination).
     *
     * @param <T>             The issues list type
     * @param startAt         The offset for the current page
     * @param maxResults      The number of results per page
     * @param searchFunction  The function to call to fetch a page
     * @param accumulator     The list accumulating all issues
     * @param total           The total number of results (null until first page is fetched)
     * @return A CompletableFuture containing all accumulated issues
     */
    private static <T extends Issue> CompletableFuture<List<T>> fetchPagesRecursivelyV2(
            Integer startAt,
            Integer maxResults,
            BiFunction<Integer, Integer, CompletableFuture<Issues<T>>> searchFunction,
            List<T> accumulator,
            Integer total) {

        return searchFunction.apply(startAt, maxResults).thenCompose(issues -> {
            // Add current page results to accumulator
            accumulator.addAll(issues);

            // Get total from first page
            Integer totalResults = total != null ? total : issues.getTotal();

            // Calculate next offset
            Integer nextStartAt = startAt + issues.size();

            // Check if there are more pages (v2 style)
            if (totalResults == null || nextStartAt >= totalResults) {
                // This is the last page, return accumulated results
                return CompletableFuture.completedFuture(accumulator);
            } else {
                // Fetch next page recursively
                return fetchPagesRecursivelyV2(nextStartAt, maxResults, searchFunction, accumulator, totalResults);
            }
        });
    }

    /**
     * Checks if a result set has more pages available.
     * This method automatically detects whether v2 or v3 pagination is being used:
     * <ul>
     *   <li><b>v3</b>: Checks {@code isLast} field and {@code nextPageToken}</li>
     *   <li><b>v2</b>: Checks {@code startAt}, {@code maxResults}, and {@code total}</li>
     * </ul>
     *
     * @param result The result object from a search response
     * @return true if more pages are available, false otherwise
     */
    public static boolean hasMorePages(Result result) {
        // Try v3 pagination first
        Boolean isLast = result.getIsLast();
        String nextToken = result.getNextPageToken();

        if (isLast != null) {
            // v3 pagination detected
            return !isLast && nextToken != null;
        }

        // Fall back to v2 pagination
        Integer startAt = result.getStartAt();
        Integer maxResults = result.getMaxResults();
        Integer total = result.getTotal();

        if (startAt != null && maxResults != null && total != null) {
            // v2 pagination detected
            return (startAt + maxResults) < total;
        }

        // Unable to determine, assume no more pages
        return false;
    }

}

