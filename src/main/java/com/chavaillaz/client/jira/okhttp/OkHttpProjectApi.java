package com.chavaillaz.client.jira.okhttp;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.JiraConstants.ApiVersion;
import com.chavaillaz.client.jira.api.ProjectApi;
import com.chavaillaz.client.jira.domain.*;
import okhttp3.OkHttpClient;


public class OkHttpProjectApi extends AbstractOkHttpClient implements ProjectApi {

    /**
     * Creates a new {@link ProjectApi} using OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira (including API version path)
     * @param authentication The authentication information
     * @param apiVersion     The API version to use
     */
    public OkHttpProjectApi(OkHttpClient client, String baseUrl, Authentication authentication, ApiVersion apiVersion) {
        super(client, baseUrl, authentication, apiVersion);
    }

    // ...existing code...
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
    public CompletableFuture<Role> getProjectRole(String projectKey, String roleId) {
        return sendAsync(requestBuilder(URL_PROJECT_ROLE, projectKey, roleId).get(), Role.class);
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
