package com.chavaillaz.client.jira.api.expand;

import static java.util.stream.Collectors.joining;

import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Expand flags for comments.
 */
@Getter
@AllArgsConstructor
public enum CommentExpand {

    /**
     * Option to show field values in HTML format.
     */
    RENDERED_BODY("renderedBody");

    private final String parameter;

    /**
     * Concatenates the given comment expansion flags with a comma separating them.
     *
     * @param expandFlags The flags to expand comments
     * @return The concatenation of the given flags
     */
    public static String asParameter(Set<CommentExpand> expandFlags) {
        return expandFlags.stream()
                .filter(Objects::nonNull)
                .map(CommentExpand::getParameter)
                .collect(joining(","));
    }

}
