package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectCategory extends Identity {

    private String description;

    /**
     * Creates a new project category.
     *
     * @param key The category key
     * @return The corresponding category
     */
    public static ProjectCategory fromId(String key) {
        ProjectCategory projectCategory = new ProjectCategory();
        projectCategory.setKey(key);
        return projectCategory;
    }

    /**
     * Creates a new project category.
     *
     * @param name The category name
     * @return The corresponding category
     */
    public static ProjectCategory fromName(String name) {
        ProjectCategory projectCategory = new ProjectCategory();
        projectCategory.setName(name);
        return projectCategory;
    }

    /**
     * Creates a new project category.
     *
     * @param key  The category key
     * @param name The category name
     * @return The corresponding category
     */
    public static ProjectCategory from(String key, String name) {
        ProjectCategory projectCategory = new ProjectCategory();
        projectCategory.setKey(key);
        projectCategory.setName(name);
        return projectCategory;
    }

}
