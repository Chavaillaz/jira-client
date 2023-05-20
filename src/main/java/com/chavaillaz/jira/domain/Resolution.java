package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resolution {

    private String description;

    private String id;

    private String name;

    private String self;

    /**
     * Creates a new resolution.
     *
     * @param id The resolution identifier
     * @return The corresponding resolution
     */
    public static Resolution fromId(String id) {
        Resolution resolution = new Resolution();
        resolution.setId(id);
        return resolution;
    }

    /**
     * Creates a new resolution.
     *
     * @param name The resolution name
     * @return The corresponding resolution
     */
    public static Resolution fromName(String name) {
        Resolution resolution = new Resolution();
        resolution.setName(name);
        return resolution;
    }

    /**
     * Creates a new resolution.
     *
     * @param id   The resolution identifier
     * @param name The resolution name
     * @return The corresponding resolution
     */
    public static Resolution from(String id, String name) {
        Resolution resolution = new Resolution();
        resolution.setId(id);
        resolution.setName(name);
        return resolution;
    }

}
