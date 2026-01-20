package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Represents pagination information for search results.
 * This class is used for both v2 and v3 APIs:
 * <ul>
 *   <li><b>v2</b>: Uses startAt, maxResults, total for pagination</li>
 *   <li><b>v3</b>: Uses maxResults, isLast, nextPageToken for pagination</li>
 * </ul>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    private Integer maxResults;

    /**
     * The page offset (v2 only).
     * For v3, pagination uses nextPageToken instead.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer startAt;

    /**
     * The total number of results (v2 only).
     * For v3, use isLast field instead.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;

    /**
     * Whether this is the last page of results (v3 only).
     * When true, there are no more pages to fetch.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLast;

    /**
     * The token to use for fetching the next page (v3 only).
     * Null for the first page or when isLast is true.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nextPageToken;

}
