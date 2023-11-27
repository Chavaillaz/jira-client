package com.chavaillaz.client.jira.java;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.domain.Components;
import com.chavaillaz.client.jira.domain.Identity;
import com.chavaillaz.client.jira.domain.Project;
import com.chavaillaz.client.jira.domain.ProjectChange;
import com.chavaillaz.client.jira.domain.Projects;
import com.chavaillaz.client.jira.domain.Roles;
import com.chavaillaz.client.jira.domain.Statuses;
import com.chavaillaz.client.jira.domain.Versions;

public class JavaHttpProjectApi extends AbstractJavaHttpClient implements ProjectApi {

    /**
     * Creates a new {@link ProjectApi} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public JavaHttpProjectApi(HttpClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Identity> addProject(ProjectChange project) {
        return sendAsync(requestBuilder(URL_PROJECTS).POST(body(project)), Identity.class);
    }

    @Override
    public CompletableFuture<Projects> getProjects(boolean includeArchived, String expand) {
        return sendAsync(requestBuilder(URL_PROJECTS_DETAILS, includeArchived, expand).GET(), Projects.class);
    }

    @Override
    public CompletableFuture<Project> getProject(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT, projectKey).GET(), Project.class);
    }

    @Override
    public CompletableFuture<Versions> getProjectVersions(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_VERSIONS, projectKey).GET(), Versions.class);
    }

    @Override
    public CompletableFuture<Components> getProjectComponents(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_COMPONENTS, projectKey).GET(), Components.class);
    }

    @Override
    public CompletableFuture<Statuses> getProjectStatuses(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_STATUSES, projectKey).GET(), Statuses.class);
    }

    @Override
    public CompletableFuture<Roles> getProjectRoles(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_ROLES, projectKey).GET(), Roles.class);
    }

    @Override
    public CompletableFuture<Project> updateProject(String projectKey, ProjectChange project) {
        return sendAsync(requestBuilder(URL_PROJECT, projectKey).PUT(body(project)), Project.class);
    }

    @Override
    public CompletableFuture<Void> deleteProject(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT, projectKey).DELETE(), Void.class);
    }

}
