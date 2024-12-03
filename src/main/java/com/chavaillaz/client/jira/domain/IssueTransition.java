package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueTransition<F extends Fields> {

    private F fields;

    private Transition transition;

    /**
     * Creates a new issue transition.
     *
     * @param transitionId The transition identifier
     * @return The corresponding transition
     */
    public static IssueTransition<Fields> fromId(String transitionId) {
        return from(Transition.fromId(transitionId));
    }

    /**
     * Creates a new issue transition.
     *
     * @param transition The transition
     * @return The corresponding transition
     */
    public static IssueTransition<Fields> from(Transition transition) {
        IssueTransition<Fields> issueTransition = new IssueTransition<>();
        issueTransition.setTransition(transition);
        issueTransition.setFields(new Fields());
        return issueTransition;
    }

}
