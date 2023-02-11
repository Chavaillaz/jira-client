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
public class Query extends Result {

    private List<String> expand;

    private List<String> fields;

    private String jql;

    /**
     * Creates a new query.
     *
     * @param jql        The query as Jira Query Language
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @param expand     The list of parameters to expand
     * @return The corresponding query
     */
    public static Query from(String jql, Integer startAt, Integer maxResults, List<String> expand) {
        return from(jql, startAt, maxResults, expand, List.of("*all"));
    }

    /**
     * Creates a new query.
     *
     * @param jql        The query as Jira Query Language
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @param expand     The list of parameters to expand
     * @param fields     The fields to load of the issues
     * @return The corresponding query
     */
    public static Query from(String jql, Integer startAt, Integer maxResults, List<String> expand, List<String> fields) {
        Query query = new Query();
        query.setJql(jql);
        query.setStartAt(startAt);
        query.setMaxResults(maxResults);
        query.setExpand(expand);
        query.setFields(fields);
        return query;
    }

}
