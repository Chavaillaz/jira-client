package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Component extends Identity {

    private String description;

    /**
     * Creates a new component.
     *
     * @param id The component identifier
     * @return The corresponding component
     */
    public static Component fromId(String id) {
        Component component = new Component();
        component.setId(id);
        return component;
    }

    /**
     * Creates a new component.
     *
     * @param name The component name
     * @return The corresponding component
     */
    public static Component fromName(String name) {
        Component component = new Component();
        component.setName(name);
        return component;
    }

}
