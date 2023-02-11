package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

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
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transitions extends Identity implements List<Transition> {

    @Delegate
    @JsonMerge
    private final List<Transition> transitions = new ArrayList<>();

    /**
     * Creates a new list of transitions.
     *
     * @param transitions The transitions to include in the list
     * @return The corresponding list of transitions
     */
    public static Transitions from(Transition... transitions) {
        return from(List.of(transitions));
    }

    /**
     * Creates a new list of transitions.
     *
     * @param transitions The transitions to include in the list
     * @return The corresponding list of transitions
     */
    public static Transitions from(List<Transition> transitions) {
        Transitions container = new Transitions();
        container.addAll(transitions);
        return container;
    }

}
