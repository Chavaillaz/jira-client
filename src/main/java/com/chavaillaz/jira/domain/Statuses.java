package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonFormat(shape = ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Statuses extends Identity implements List<IssueType> {

    @Delegate
    @JsonMerge
    private final List<IssueType> statuses = new ArrayList<>();

    /**
     * Creates a new list of statuses.
     *
     * @param issueTypes The statuses to include in the list
     * @return The corresponding list of statuses
     */
    public static Statuses from(IssueType... issueTypes) {
        return from(List.of(issueTypes));
    }

    /**
     * Creates a new list of statuses.
     *
     * @param issueTypes The statuses to include in the list
     * @return The corresponding list of statuses
     */
    public static Statuses from(List<IssueType> issueTypes) {
        Statuses container = new Statuses();
        container.addAll(issueTypes);
        return container;
    }

}
