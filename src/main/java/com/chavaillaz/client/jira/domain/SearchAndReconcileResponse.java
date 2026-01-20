package com.chavaillaz.client.jira.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Represents a search response from API v3 using the /search/jql endpoint.
 * This follows the SearchAndReconcileResults schema from CHANGE-2046.
 * <p>
 * Key differences from v2:
 * - Uses {@code nextPageToken} instead of {@code startAt} for pagination
 * - Uses {@code isLast} instead of calculating from {@code total}
 * - No {@code total}, {@code startAt}, or {@code maxResults} fields
 *
 * @param <T> The type of issue in the result
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAndReconcileResponse<T> {

    private List<T> issues;

    private String nextPageToken;

    private Boolean isLast;

    private Map<String, String> names;

    private Map<String, Object> schema;

}

