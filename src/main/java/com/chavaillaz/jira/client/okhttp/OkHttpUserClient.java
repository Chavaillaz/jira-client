package com.chavaillaz.jira.client.okhttp;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.User;
import com.chavaillaz.jira.domain.Users;
import okhttp3.OkHttpClient;

public class OkHttpUserClient extends AbstractOkHttpClient implements UserClient {

    /**
     * Creates a new {@link UserClient} using OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     */
    public OkHttpUserClient(OkHttpClient client, String baseUrl, String authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Users> getUsers(String search, Integer startAt, Integer maxResults, Boolean includeInactive) {
        return sendAsync(requestBuilder(URL_USERS_SEARCH, search, startAt, maxResults, includeInactive).get(), Users.class);
    }

    @Override
    public CompletableFuture<Users> getAssignableUsers(String search, String key, Integer startAt, Integer maxResults, Boolean includeInactive) {
        String url = key.contains("-") ? URL_USERS_ISSUE_ASSIGNABLE : URL_USERS_PROJECT_ASSIGNABLE;
        return sendAsync(requestBuilder(url, search, key, startAt, maxResults, includeInactive).get(), Users.class);
    }

    @Override
    public CompletableFuture<User> getUser(String username) {
        return sendAsync(requestBuilder(URL_USER, username).get(), User.class);
    }

    @Override
    public CompletableFuture<User> getCurrentUser() {
        return sendAsync(requestBuilder(URL_CURRENT_USER).get(), User.class);
    }

}
