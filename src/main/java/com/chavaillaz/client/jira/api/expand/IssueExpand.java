package com.chavaillaz.client.jira.api.expand;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Expand flags for issues.
 */
@Getter
@AllArgsConstructor
public enum IssueExpand {

    /**
     * Option to show field values in HTML format.
     */
    RENDERED_FIELDS("renderedFields"),

    /**
     * Option to display name of each field.
     */
    NAMES("names"),

    /**
     * Option to get schema for each field which describes a type of the field.
     */
    SCHEMA("schema"),

    /**
     * Option to get all possible transitions for the given issue.
     */
    TRANSITIONS("transitions"),

    /**
     * Option to get all possibles operations which may be applied on issue.
     */
    OPERATIONS("operations"),

    /**
     * Option to get information about how each field may be edited.
     * It contains field's schema as well.
     */
    EDIT_META("editmeta"),

    /**
     * Option to get the history of all changes of the given issue.
     */
    CHANGELOG("changelog"),

    /**
     * Option to get REST representations of all fields.
     * Some field may contain more recent versions.
     * RESET representations are numbered.
     * The greatest number always represents the most recent version.
     * It is recommended that the most recent version is used.
     * version for these fields which provide a more recent REST representation.
     * After including versionedRepresentations "fields" field become hidden.
     */
    VERSIONED_REPRESENTATIONS("versionedRepresentations");

    private final String parameter;

    /**
     * Concatenates the given issue expansion flags with a comma separating them.
     *
     * @param expand The flags to expand issues
     * @return The concatenation of the given flags
     */
    public static String getParameters(IssueExpand... expand) {
        return Arrays.stream(expand)
                .filter(Objects::nonNull)
                .map(IssueExpand::getParameter)
                .collect(joining(","));
    }

}