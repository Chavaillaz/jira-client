package com.chavaillaz.client.jira.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.UserApi;
import com.chavaillaz.client.jira.domain.User;
import com.chavaillaz.client.jira.domain.Users;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

public class ApacheHttpUserApi extends AbstractApacheHttpClient implements UserApi {

    /**
     * Creates a new {@link UserApi} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public ApacheHttpUserApi(CloseableHttpAsyncClient client, String baseUrl, Authentication authentication) {
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
