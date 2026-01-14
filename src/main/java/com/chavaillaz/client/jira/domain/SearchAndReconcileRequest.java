package com.chavaillaz.client.jira.domain;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.chavaillaz.client.jira.api.expand.IssueExpand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Represents a search request for API v3 using the /search/jql endpoint.
 * This follows the SearchAndReconcileRequestBean schema from CHANGE-2046.
 * <p>
 * Key differences from v2:
 * - Uses {@code nextPageToken} instead of {@code startAt} for pagination
 * - Includes {@code reconcileIssues} for strong consistency
 * - Supports {@code fieldsByKeys} parameter
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAndReconcileRequest {

    private String jql;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nextPageToken;

    private Integer maxResults;

    /**
     * Expand parameter as a comma-separated string (or empty string).
     * The v3 API expects a string, not an array.
     */
    private String expand;

    private List<String> fields;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> properties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean fieldsByKeys;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> reconcileIssues;

    /**
     * Creates a new search request for API v3.
     *
     * @param jql             The query as Jira Query Language
     * @param nextPageToken   The token for the next page (null for first page)
     * @param maxResults      The number of results per page
     * @param expand          The list of parameters to expand
     * @param fields          The fields to load of the issues
     * @return The corresponding search request
     */
    public static SearchAndReconcileRequest from(String jql, String nextPageToken, Integer maxResults, Set<IssueExpand> expand, Set<String> fields) {
        SearchAndReconcileRequest request = new SearchAndReconcileRequest();
        request.setJql(jql);
        request.setNextPageToken(nextPageToken);
        request.setMaxResults(maxResults);

        // Convert expand Set to comma-separated string (or empty string)
        String expandStr = Optional.ofNullable(expand)
                .stream()
                .flatMap(Collection::stream)
                .map(IssueExpand::getParameter)
                .collect(java.util.stream.Collectors.joining(","));
        request.setExpand(expandStr);

        // Convert fields Set to List
        List<String> fieldsList = Optional.ofNullable(fields)
                .map(f -> f.stream().collect(toList()))
                .orElse(emptyList());
        request.setFields(fieldsList);

        // Set fieldsByKeys to false (standard behavior)
        request.setFieldsByKeys(false);

        return request;
    }

}

