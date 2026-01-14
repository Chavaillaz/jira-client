package com.chavaillaz.client.jira.domain;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.chavaillaz.client.jira.api.expand.IssueExpand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents a search query for JIRA API.
 * This class is used for both v2 and v3 APIs, but with different serialization:
 * <ul>
 *   <li><b>v2</b>: Includes all fields (jql, startAt, maxResults, expand, fields)</li>
 *   <li><b>v3</b>: Excludes startAt (handled by nextPageToken in SearchAndReconcileRequest)</li>
 * </ul>
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query extends Result {

    private Set<String> expand;

    private Set<String> fields;

    private String jql;

    /**
     * The token for the next page in API v3.
     * This field is used instead of startAt for v3 pagination.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nextPageToken;

    /**
     * Creates a new query for API v2.
     *
     * @param jql        The query as Jira Query Language
     * @param startAt    The page offset (v2 only)
     * @param maxResults The number of results per page
     * @param expand     The list of parameters to expand
     * @param fields     The fields to load of the issues
     * @return The corresponding query
     */
    public static Query from(String jql, Integer startAt, Integer maxResults, Set<IssueExpand> expand, Set<String> fields) {
        Query query = new Query();
        query.setJql(jql);
        query.setStartAt(startAt);
        query.setMaxResults(maxResults);
        query.setExpand(Optional.ofNullable(expand)
                .stream()
                .flatMap(Collection::stream)
                .map(IssueExpand::getParameter)
                .collect(toSet()));
        query.setFields(Optional.ofNullable(fields)
                .orElse(emptySet()));
        return query;
    }

    /**
     * Creates a new query for API v3.
     * Uses nextPageToken instead of startAt for pagination.
     *
     * @param jql           The query as Jira Query Language
     * @param nextPageToken The token for the next page (null for first page)
     * @param maxResults    The number of results per page
     * @param expand        The list of parameters to expand
     * @param fields        The fields to load of the issues
     * @return The corresponding query
     */
    public static Query fromV3(String jql, String nextPageToken, Integer maxResults, Set<IssueExpand> expand, Set<String> fields) {
        Query query = new Query();
        query.setJql(jql);
        query.setNextPageToken(nextPageToken);
        query.setMaxResults(maxResults);
        // Don't set startAt for v3
        query.setExpand(Optional.ofNullable(expand)
                .stream()
                .flatMap(Collection::stream)
                .map(IssueExpand::getParameter)
                .collect(toSet()));
        query.setFields(Optional.ofNullable(fields)
                .orElse(emptySet()));
        return query;
    }

}
