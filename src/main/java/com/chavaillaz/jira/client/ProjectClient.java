package com.chavaillaz.jira.client;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.domain.Components;
import com.chavaillaz.jira.domain.Identity;
import com.chavaillaz.jira.domain.Project;
import com.chavaillaz.jira.domain.ProjectChange;
import com.chavaillaz.jira.domain.Projects;
import com.chavaillaz.jira.domain.Roles;
import com.chavaillaz.jira.domain.Statuses;
import com.chavaillaz.jira.domain.Versions;

public interface ProjectClient extends AutoCloseable {

    String URL_PROJECTS = "project";
    String URL_PROJECTS_DETAILS = "project?includeArchived={0}&expand={1}";
    String URL_PROJECT = "project/{0}";
    String URL_PROJECT_VERSIONS = "project/{0}/versions";
    String URL_PROJECT_COMPONENTS = "project/{0}/components";
    String URL_PROJECT_STATUSES = "project/{0}/statuses";
    String URL_PROJECT_ROLES = "project/{0}/role";

    /**
     * Creates a new project.
     *
     * @param project The project
     * @return A {@link CompletableFuture} with the created project
     */
    CompletableFuture<Identity> addProject(ProjectChange project);

    /**
     * Gets all the visible projects.
     *
     * @return A {@link CompletableFuture} with the projects
     */
    default CompletableFuture<Projects> getProjects() {
        return getProjects(false, null);
    }

    /**
     * Gets all the visible project.
     *
     * @param includeArchived Whether to include archived projects in response
     * @param expand          The parameters to expand (comma separated)
     * @return A {@link CompletableFuture} with the projects
     */
    CompletableFuture<Projects> getProjects(boolean includeArchived, String expand);

    /**
     * Gets a specific project.
     *
     * @param projectKey The project key
     * @return A {@link CompletableFuture} with the project
     */
    CompletableFuture<Project> getProject(String projectKey);

    /**
     * Gets the list of available versions in a project.
     *
     * @param projectKey The project key
     * @return A {@link CompletableFuture} with the versions
     */
    CompletableFuture<Versions> getProjectVersions(String projectKey);

    /**
     * Gets the list of available components in a project.
     *
     * @param projectKey The project key
     * @return A {@link CompletableFuture} with the components
     */
    CompletableFuture<Components> getProjectComponents(String projectKey);

    /**
     * Gets the list of available status in a project.
     *
     * @param projectKey The project key
     * @return A {@link CompletableFuture} with the status
     */
    CompletableFuture<Statuses> getProjectStatuses(String projectKey);

    /**
     * Gets the list of available roles in a project.
     *
     * @param projectKey The project key
     * @return A {@link CompletableFuture} with the roles
     */
    CompletableFuture<Roles> getProjectRoles(String projectKey);

    /**
     * Updates a project.
     *
     * @param projectKey The project key
     * @param project    The project to update
     * @return A {@link CompletableFuture} with the updated project
     */
    CompletableFuture<Project> updateProject(String projectKey, ProjectChange project);

    /**
     * Deletes a project.
     *
     * @param projectKey The project key
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteProject(String projectKey);

}