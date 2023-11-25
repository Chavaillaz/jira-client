package com.chavaillaz.jira.client.java;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.JiraAuthentication;
import com.chavaillaz.jira.client.SearchClient;
import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.User;
import com.chavaillaz.jira.domain.Users;

public class JavaHttpUserClient extends AbstractJavaHttpClient implements UserClient {

    /**
     * Creates a new {@link SearchClient} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public JavaHttpUserClient(HttpClient client, String baseUrl, JiraAuthentication authentication) {
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
