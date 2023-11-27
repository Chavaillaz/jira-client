package com.chavaillaz.client.jira.java;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.SearchApi;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.domain.User;
import com.chavaillaz.client.jira.domain.Users;

public class JavaHttpUserApi extends AbstractJavaHttpClient implements UserApi {

    /**
     * Creates a new {@link SearchApi} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public JavaHttpUserApi(HttpClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Users> getUsers(String search, Integer startAt, Integer maxResults, Boolean includeInactive) {
        return sendAsync(requestBuilder(URL_USERS_SEARCH, search, startAt, maxResults, includeInactive).GET(), Users.class);
    }

    @Override
    public CompletableFuture<Users> getAssignableUsers(String search, String key, Integer startAt, Integer maxResults, Boolean includeInactive) {
        String url = key.contains("-") ? URL_USERS_ISSUE_ASSIGNABLE : URL_USERS_PROJECT_ASSIGNABLE;
        return sendAsync(requestBuilder(url, search, key, startAt, maxResults, includeInactive).GET(), Users.class);
    }

    @Override
    public CompletableFuture<User> getUser(String username) {
        return sendAsync(requestBuilder(URL_USER, username).GET(), User.class);
    }

    @Override
    public CompletableFuture<User> getCurrentUser() {
        return sendAsync(requestBuilder(URL_CURRENT_USER).GET(), User.class);
    }

}
