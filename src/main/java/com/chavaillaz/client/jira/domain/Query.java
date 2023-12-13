package com.chavaillaz.client.jira.domain;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.chavaillaz.client.jira.api.expand.IssueExpand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query extends Result {

    private Set<String> expand;

    private Set<String> fields;

    private String jql;

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

}
