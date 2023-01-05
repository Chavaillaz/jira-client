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

    /**
     * Creates a new transition.
     *
     * @param name The transition name
     * @return The corresponding transition
     */
    public static Transition fromName(String name) {
        Transition transition = new Transition();
        transition.setName(name);
        return transition;
    }

}
