package com.chavaillaz.client.jira.okhttp;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.JiraConstants.ApiVersion;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.domain.User;
import com.chavaillaz.client.jira.domain.Users;
import okhttp3.OkHttpClient;

public class OkHttpUserApi extends AbstractOkHttpClient implements UserApi {

    /**
     * Creates a new {@link UserApi} using OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira (including API version path)
     * @param authentication The authentication information
     * @param apiVersion     The API version to use
     */
    public OkHttpUserApi(OkHttpClient client, String baseUrl, Authentication authentication, ApiVersion apiVersion) {
        super(client, baseUrl, authentication, apiVersion);
    }

    // ...existing code...
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
