package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group implements Comparable<Group> {

    private String name;

    private String self;

    /**
     * Creates a new group.
     *
     * @param name The group name
     * @return The corresponding group
     */
    public static Group fromName(String name) {
        Group group = new Group();
        group.setName(name);
        return group;
    }

    @Override
    public int compareTo(Group other) {
        return getName().compareTo(other.getName());
    }

}
