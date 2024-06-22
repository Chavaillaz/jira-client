package com.chavaillaz.client.jira.domain;

import static java.util.Collections.emptyList;

import java.util.List;

import lombok.Getter;

/**
 * Represents a transition to a status in the workflow of an issue type.
 */
@Getter
public class StatusTransition {

    private final List<StatusDefinition<?>> sourceStatuses;
    private final String transitionId;

    /**
     * Creates a transition.
     *
     * @param sourceStatuses The list of status from which the transition is done to the target one
     * @param transitionId   The corresponding transition identifier
     */
    public StatusTransition(List<StatusDefinition<?>> sourceStatuses, String transitionId) {
        this.sourceStatuses = sourceStatuses;
        this.transitionId = transitionId;
    }

    /**
     * Creates a transition.
     *
     * @param sourceStatus The status from which the transition is done to the target one
     * @param transitionId The corresponding transition identifier
     */
    public StatusTransition(StatusDefinition<?> sourceStatus, String transitionId) {
        this(List.of(sourceStatus), transitionId);
    }

    /**
     * Creates a wildcard transition, meaning every status can be pushed to the target status with this transition.
     *
     * @param transitionId The corresponding transition identifier
     */
    public StatusTransition(String transitionId) {
        this(emptyList(), transitionId);
    }

    /**
     * Indicates if the current transition is accepting to be made from the given source status.
     *
     * @param sourceStatus The source status from which the transition is desired
     * @return {@code true} if this transition is allowed for the given status, {@code false} otherwise
     */
    public boolean isAllowedFrom(StatusDefinition<?> sourceStatus) {
        return this.sourceStatuses.isEmpty() || this.sourceStatuses.contains(sourceStatus);
    }

}
