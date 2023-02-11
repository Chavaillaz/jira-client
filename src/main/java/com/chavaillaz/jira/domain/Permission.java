package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Permission {

    private boolean edit;

    private Group group;

    private int id;

    private Project project;

    private String type;

    private User user;

    private boolean view;

    /**
     * Creates a new permission for a given user.
     *
     * @param user The user which is granted this permission
     * @param view The right to view the element
     * @param edit The right to edit the element
     * @return The corresponding permission
     */
    public static Permission fromUser(User user, boolean view, boolean edit) {
        Permission permission = new Permission();
        permission.setType("user");
        permission.setUser(user);
        permission.setView(view);
        permission.setEdit(edit);
        return permission;
    }

    /**
     * Creates a new permission for a given group.
     *
     * @param group The group which is granted this permission
     * @param view  The right to view the element
     * @param edit  The right to edit the element
     * @return The corresponding permission
     */
    public static Permission fromGroup(Group group, boolean view, boolean edit) {
        Permission permission = new Permission();
        permission.setType("group");
        permission.setGroup(group);
        permission.setView(view);
        permission.setEdit(edit);
        return permission;
    }

    /**
     * Creates a new permission for a given project.
     *
     * @param project The group which is granted this permission
     * @param view    The right to view the element
     * @param edit    The right to edit the element
     * @return The corresponding permission
     */
    public static Permission fromProject(Project project, boolean view, boolean edit) {
        Permission permission = new Permission();
        permission.setType("project");
        permission.setProject(project);
        permission.setView(view);
        permission.setEdit(edit);
        return permission;
    }

}
