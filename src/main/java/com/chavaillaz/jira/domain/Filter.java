package com.chavaillaz.jira.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter extends Identity {

    private String description;

    private Boolean favourite;

    private String jql;

    private User owner;

    private String searchUrl;

    private List<Object> sharePermissions;

    private SharedUsers sharedUsers;

    private Subscriptions subscriptions;

    private String viewUrl;

    /**
     * Creates a new filter.
     *
     * @param name The filter name
     * @param jql  The filter query
     * @return The corresponding filter
     */
    public static Filter from(String name, String jql) {
        return from(name, null, jql);
    }

    /**
     * Creates a new filter.
     *
     * @param name        The filter name
     * @param description The filter description
     * @param jql         The filter query
     * @return The corresponding filter
     */
    public static Filter from(String name, String description, String jql) {
        Filter filter = new Filter();
        filter.setName(name);
        filter.setDescription(description);
        filter.setJql(jql);
        filter.setFavourite(true);
        return filter;
    }

}
