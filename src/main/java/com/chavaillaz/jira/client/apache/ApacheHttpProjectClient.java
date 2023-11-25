package com.chavaillaz.jira.client.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.delete;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.put;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.JiraAuthentication;
import com.chavaillaz.jira.client.ProjectClient;
import com.chavaillaz.jira.domain.Components;
import com.chavaillaz.jira.domain.Identity;
import com.chavaillaz.jira.domain.Project;
import com.chavaillaz.jira.domain.ProjectChange;
import com.chavaillaz.jira.domain.Projects;
import com.chavaillaz.jira.domain.Roles;
import com.chavaillaz.jira.domain.Statuses;
import com.chavaillaz.jira.domain.Versions;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

public class ApacheHttpProjectClient extends AbstractApacheHttpClient implements ProjectClient {

    /**
     * Creates a new {@link ProjectClient} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public ApacheHttpProjectClient(CloseableHttpAsyncClient client, String baseUrl, JiraAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Identity> addProject(ProjectChange project) {
        return sendAsync(requestBuilder(post(), URL_PROJECTS)
                .setBody(serialize(project), APPLICATION_JSON), Identity.class);
    }

    @Override
    public CompletableFuture<Projects> getProjects(boolean includeArchived, String expand) {
        return sendAsync(requestBuilder(get(), URL_PROJECTS_DETAILS, includeArchived, expand), Projects.class);
    }

    @Override
    public CompletableFuture<Project> getProject(String projectKey) {
        return sendAsync(requestBuilder(get(), URL_PROJECT, projectKey), Project.class);
    }

    @Override
    public CompletableFuture<Versions> getProjectVersions(String projectKey) {
        return sendAsync(requestBuilder(get(), URL_PROJECT_VERSIONS, projectKey), Versions.class);
    }

    @Override
    public CompletableFuture<Components> getProjectComponents(String projectKey) {
        return sendAsync(requestBuilder(get(), URL_PROJECT_COMPONENTS, projectKey), Components.class);
    }

    @Override
    public CompletableFuture<Statuses> getProjectStatuses(String projectKey) {
        return sendAsync(requestBuilder(get(), URL_PROJECT_STATUSES, projectKey), Statuses.class);
    }

    @Override
    public CompletableFuture<Roles> getProjectRoles(String projectKey) {
        return sendAsync(requestBuilder(get(), URL_PROJECT_ROLES, projectKey), Roles.class);
    }

    @Override
    public CompletableFuture<Project> updateProject(String projectKey, ProjectChange project) {
        return sendAsync(requestBuilder(put(), URL_PROJECT, projectKey)
                .setBody(serialize(project), APPLICATION_JSON), Project.class);
    }

    @Override
    public CompletableFuture<Void> deleteProject(String projectKey) {
        return sendAsync(requestBuilder(delete(), URL_PROJECT, projectKey), Void.class);
    }

}
