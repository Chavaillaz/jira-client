package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueTransition {

    private Transition transition;

    /**
     * Creates a new issue transition.
     *
     * @param transitionId The transition identifier
     * @return The corresponding transition
     */
    public static IssueTransition fromId(String transitionId) {
        return from(Transition.fromId(transitionId));
    }

    /**
     * Creates a new issue transition.
     *
     * @param transition The transition
     * @return The corresponding transition
     */
    public static IssueTransition from(Transition transition) {
        return new IssueTransition(transition);
    }

}
