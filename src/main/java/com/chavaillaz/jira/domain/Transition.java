package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transition {

    private String id;

    private String name;

    private Status to;

    /**
     * Creates a new transition.
     *
     * @param id The transition identifier
     * @return The corresponding transition
     */
    public static Transition fromId(String id) {
        Transition transition = new Transition();
        transition.setId(id);
        return transition;
    }

}
