package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Priority extends Identity {

    private String iconUrl;

    /**
     * Creates a new priority.
     *
     * @param id The priority identifier
     * @return The corresponding priority
     */
    public static Priority fromId(String id) {
        Priority priority = new Priority();
        priority.setId(id);
        return priority;
    }

    /**
     * Creates a new priority.
     *
     * @param name The priority name
     * @return The corresponding priority
     */
    public static Priority fromName(String name) {
        Priority priority = new Priority();
        priority.setName(name);
        return priority;
    }

    /**
     * Creates a new priority.
     *
     * @param id   The priority identifier
     * @param name The priority name
     * @return The corresponding priority
     */
    public static Priority from(String id, String name) {
        Priority priority = new Priority();
        priority.setId(id);
        priority.setName(name);
        return priority;
    }

}
