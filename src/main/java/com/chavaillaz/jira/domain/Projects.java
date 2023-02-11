package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Projects implements List<Project> {

    @Delegate
    @JsonMerge
    private final List<Project> projects = new ArrayList<>();

    /**
     * Creates a new list of projects.
     *
     * @param projects The projects to include in the list
     * @return The corresponding list of projects
     */
    public static Projects from(Project... projects) {
        return from(List.of(projects));
    }

    /**
     * Creates a new list of projects.
     *
     * @param projects The projects to include in the list
     * @return The corresponding list of projects
     */
    public static Projects from(List<Project> projects) {
        Projects container = new Projects();
        container.addAll(projects);
        return container;
    }

}
