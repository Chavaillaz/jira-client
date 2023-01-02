package com.chavaillaz.jira.client.okhttp;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.domain.Components;
import com.chavaillaz.jira.domain.Identity;
import com.chavaillaz.jira.domain.Project;
import com.chavaillaz.jira.domain.ProjectChange;
import com.chavaillaz.jira.domain.Projects;
import com.chavaillaz.jira.domain.Roles;
import com.chavaillaz.jira.domain.Statuses;
import com.chavaillaz.jira.domain.Versions;
import okhttp3.OkHttpClient;

public class OkHttpProjectClient extends AbstractOkHttpClient implements ProjectClient {

    /**
     * Creates a new {@link ProjectClient} using OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     */
    public OkHttpProjectClient(OkHttpClient client, String baseUrl, String authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Identity> addProject(ProjectChange project) {
        return sendAsync(requestBuilder(URL_PROJECTS).post(body(project)), Identity.class);
    }

    @Override
    public CompletableFuture<Projects> getProjects(boolean includeArchived, String expand) {
        return sendAsync(requestBuilder(URL_PROJECTS_DETAILS, includeArchived, expand).get(), Projects.class);
    }

    @Override
    public CompletableFuture<Project> getProject(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT, projectKey).get(), Project.class);
    }

    @Override
    public CompletableFuture<Versions> getProjectVersions(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_VERSIONS, projectKey).get(), Versions.class);
    }

    @Override
    public CompletableFuture<Components> getProjectComponents(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_COMPONENTS, projectKey).get(), Components.class);
    }

    @Override
    public CompletableFuture<Statuses> getProjectStatuses(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_STATUSES, projectKey).get(), Statuses.class);
    }

    @Override
    public CompletableFuture<Roles> getProjectRoles(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT_ROLES, projectKey).get(), Roles.class);
    }

    @Override
    public CompletableFuture<Project> updateProject(String projectKey, ProjectChange project) {
        return sendAsync(requestBuilder(URL_PROJECT, projectKey).put(body(project)), Project.class);
    }

    @Override
    public CompletableFuture<Void> deleteProject(String projectKey) {
        return sendAsync(requestBuilder(URL_PROJECT, projectKey).delete(), Void.class);
    }

}
