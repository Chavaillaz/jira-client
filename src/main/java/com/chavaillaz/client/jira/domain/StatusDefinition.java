package com.chavaillaz.client.jira.domain;

import java.util.List;

/**
 * Represents a status of the workflow from an issue type.
 *
 * @param <T> The enumeration implementing it
 */
public interface StatusDefinition<T extends Enum<T>> {

    /**
     * Gets the identifier of the status.
     *
     * @return The status identifier
     */
    String getId();

    /**
     * Gets the name of the status.
     * Note that it can change depending on the user language.
     *
     * @return The status name
     */
    String getName();

    /**
     * Gets the list of possible transitions to this status.
     *
     * @return The list of available transitions
     */
    List<StatusTransition> getTransitions();

    /**
     * Gets the transition identifier.
     * It can be used to make an issue move to this status.
     *
     * @return The transition identifier or {@code null} when not only one existing
     */
    default String getTransitionId() {
        if (getTransitions().size() == 1) {
            return getTransitions().get(0).getTransitionId();
        } else {
            return null;
        }
    }

    /**
     * Gets the transition identifier.
     * It can be used to make an issue move to this status.
     *
     * @param sourceStatus The status from which the transition is done to the target one
     * @return The transition identifier
     */
    default String getTransitionId(StatusDefinition<T> sourceStatus) {
        return getTransitions().stream()
                .filter(transition -> transition.isAllowedFrom(sourceStatus))
                .map(StatusTransition::getTransitionId)
                .findFirst()
                .orElse(null);
    }

}
