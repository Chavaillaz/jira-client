package com.chavaillaz.client.jira.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends Identity {

    private Boolean archived;

    private AssigneeType assigneeType;

    private Avatars avatarUrls;

    private List<Component> components;

    private String description;

    private List<IssueType> issueTypes;

    private User lead;

    private ProjectCategory projectCategory;

    private Roles roles;

    private String url;

    private List<Version> versions;

    /**
     * Creates a new project.
     *
     * @param id The project identifier
     * @return The corresponding project
     */
    public static Project fromId(String id) {
        Project project = new Project();
        project.setId(id);
        return project;
    }

    /**
     * Creates a new project.
     *
     * @param key The project key
     * @return The corresponding project
     */
    public static Project fromKey(String key) {
        Project project = new Project();
        project.setKey(key);
        return project;
    }

    /**
     * Creates a new project.
     *
     * @param name The project name
     * @return The corresponding project
     */
    public static Project fromName(String name) {
        Project project = new Project();
        project.setName(name);
        return project;
    }

    /**
     * Creates a new project.
     *
     * @param id   The project identifier
     * @param key  The project key
     * @param name The project name
     * @return The corresponding project
     */
    public static Project from(String id, String key, String name) {
        Project project = new Project();
        project.setId(id);
        project.setKey(key);
        project.setName(name);
        return project;
    }

}
