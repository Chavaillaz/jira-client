package com.chavaillaz.client.jira.vertx;

import static io.vertx.core.http.HttpMethod.GET;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.domain.User;
import com.chavaillaz.client.jira.domain.Users;
import io.vertx.ext.web.client.WebClient;

public class VertxHttpUserApi extends AbstractVertxHttpClient implements UserApi {

    /**
     * Creates a new {@link UserApi} using Vert.x client.
     *
     * @param client         The Vert.x client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public VertxHttpUserApi(WebClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Users> getUsers(String search, Integer startAt, Integer maxResults, Boolean includeInactive) {
        return handleAsync(requestBuilder(GET, URL_USERS_SEARCH, search, startAt, maxResults, includeInactive).send(), Users.class);
    }

    @Override
    public CompletableFuture<Users> getAssignableUsers(String search, String key, Integer startAt, Integer maxResults, Boolean includeInactive) {
        String url = key.contains("-") ? URL_USERS_ISSUE_ASSIGNABLE : URL_USERS_PROJECT_ASSIGNABLE;
        return handleAsync(requestBuilder(GET, url, search, key, startAt, maxResults, includeInactive).send(), Users.class);
    }

    @Override
    public CompletableFuture<User> getUser(String username) {
        return handleAsync(requestBuilder(GET, URL_USER, username).send(), User.class);
    }

    @Override
    public CompletableFuture<User> getCurrentUser() {
        return handleAsync(requestBuilder(GET, URL_CURRENT_USER).send(), User.class);
    }

}
