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
     * Creates a new transition.
     *
     * @param transitionId The transition identifier
     * @return The corresponding transition
     */
    public static IssueTransition fromId(String transitionId) {
        return new IssueTransition(Transition.fromId(transitionId));
    }

}
