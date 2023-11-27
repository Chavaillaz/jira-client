package com.chavaillaz.client.jira.vertx;

import static io.vertx.core.http.HttpMethod.DELETE;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;
import static io.vertx.core.http.HttpMethod.PUT;

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
import io.vertx.ext.web.client.WebClient;

public class VertxHttpProjectApi extends AbstractVertxHttpClient implements ProjectApi {

    /**
     * Creates a new {@link ProjectApi} using Vert.x client.
     *
     * @param client         The Vert.x client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public VertxHttpProjectApi(WebClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Identity> addProject(ProjectChange project) {
        return handleAsync(requestBuilder(POST, URL_PROJECTS).sendBuffer(body(project)), Identity.class);
    }

    @Override
    public CompletableFuture<Projects> getProjects(boolean includeArchived, String expand) {
        return handleAsync(requestBuilder(GET, URL_PROJECTS_DETAILS, includeArchived, expand).send(), Projects.class);
    }

    @Override
    public CompletableFuture<Project> getProject(String projectKey) {
        return handleAsync(requestBuilder(GET, URL_PROJECT, projectKey).send(), Project.class);
    }

    @Override
    public CompletableFuture<Versions> getProjectVersions(String projectKey) {
        return handleAsync(requestBuilder(GET, URL_PROJECT_VERSIONS, projectKey).send(), Versions.class);
    }

    @Override
    public CompletableFuture<Components> getProjectComponents(String projectKey) {
        return handleAsync(requestBuilder(GET, URL_PROJECT_COMPONENTS, projectKey).send(), Components.class);
    }

    @Override
    public CompletableFuture<Statuses> getProjectStatuses(String projectKey) {
        return handleAsync(requestBuilder(GET, URL_PROJECT_STATUSES, projectKey).send(), Statuses.class);
    }

    @Override
    public CompletableFuture<Roles> getProjectRoles(String projectKey) {
        return handleAsync(requestBuilder(GET, URL_PROJECT_ROLES, projectKey).send(), Roles.class);
    }

    @Override
    public CompletableFuture<Project> updateProject(String projectKey, ProjectChange project) {
        return handleAsync(requestBuilder(PUT, URL_PROJECT, projectKey).sendBuffer(body(project)), Project.class);
    }

    @Override
    public CompletableFuture<Void> deleteProject(String projectKey) {
        return handleAsync(requestBuilder(DELETE, URL_PROJECT, projectKey).send(), Void.class);
    }

}
