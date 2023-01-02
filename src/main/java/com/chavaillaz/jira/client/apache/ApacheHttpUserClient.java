package com.chavaillaz.jira.client.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.UserClient;
import com.chavaillaz.jira.domain.User;
import com.chavaillaz.jira.domain.Users;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

public class ApacheHttpUserClient extends AbstractApacheHttpClient implements UserClient {

    /**
     * Creates a new {@link UserClient} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     */
    public ApacheHttpUserClient(CloseableHttpAsyncClient client, String baseUrl, String authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Users> getUsers(String search, Integer startAt, Integer maxResults, Boolean includeInactive) {
        return sendAsync(requestBuilder(get(), URL_USERS_SEARCH, search, startAt, maxResults, includeInactive), Users.class);
    }

    @Override
    public CompletableFuture<Users> getAssignableUsers(String search, String key, Integer startAt, Integer maxResults, Boolean includeInactive) {
        String url = key.contains("-") ? URL_USERS_ISSUE_ASSIGNABLE : URL_USERS_PROJECT_ASSIGNABLE;
        return sendAsync(requestBuilder(get(), url, search, key, startAt, maxResults, includeInactive), Users.class);
    }

    @Override
    public CompletableFuture<User> getUser(String username) {
        return sendAsync(requestBuilder(get(), URL_USER, username), User.class);
    }

    @Override
    public CompletableFuture<User> getCurrentUser() {
        return sendAsync(requestBuilder(get(), URL_CURRENT_USER), User.class);
    }

}
