package com.chavaillaz.jira.client;

import static java.util.Optional.empty;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.domain.User;
import com.chavaillaz.jira.domain.Users;

public interface UserClient extends AutoCloseable {

    String URL_CURRENT_USER = "myself";
    String URL_USER = "user?username={0}";
    String URL_USERS_SEARCH = "user/search?username={0}&startAt={1}&maxResults={2}&includeInactive={3}";
    String URL_USERS_ISSUE_ASSIGNABLE = "user/assignable/search?username={0}&issueKey={1}&startAt={2}&maxResults={3}&includeInactive={4}";
    String URL_USERS_PROJECT_ASSIGNABLE = "user/assignable/search?username={0}&project={1}&startAt={2}&maxResults={3}&includeInactive={4}";

    /**
     * Gets the list of users matching the given search string.
     * This uses a page offset of 0 and a number of results par page of 50 without including inactive accounts.
     *
     * @param search The username, name or email address to search
     * @return A {@link CompletableFuture} with the users
     */
    default CompletableFuture<Users> getUsers(String search) {
        return getUsers(search, 0, 50, false);
    }

    /**
     * Gets the list of users matching the given search string.
     *
     * @param search          The username, name or email address to search
     * @param startAt         The page offset
     * @param maxResults      The number of results per page
     * @param includeInactive Whether to include inactive accounts
     * @return A {@link CompletableFuture} with the users
     */
    CompletableFuture<Users> getUsers(String search, Integer startAt, Integer maxResults, Boolean includeInactive);

    /**
     * Gets the list of assignable users for a project or an issue.
     * This uses a page offset of 0 and a number of results par page of 50 without including inactive accounts.
     *
     * @param search The username, name or email address to search
     * @param key    The project or issue key
     * @return A {@link CompletableFuture} with the users
     */
    default CompletableFuture<Users> getAssignableUsers(String search, String key) {
        return getAssignableUsers(search, key, 0, 50, false);
    }

    /**
     * Gets the list of assignable users for a project or an issue.
     *
     * @param search          The username, name or email address to search
     * @param key             The project or issue key
     * @param startAt         The page offset
     * @param maxResults      The number of results per page
     * @param includeInactive Whether to include inactive accounts
     * @return A {@link CompletableFuture} with the users
     */
    CompletableFuture<Users> getAssignableUsers(String search, String key, Integer startAt, Integer maxResults, Boolean includeInactive);

    /**
     * Gets a user.
     *
     * @param username The username
     * @return A {@link CompletableFuture} with the user
     */
    CompletableFuture<User> getUser(String username);

    /**
     * Gets a user.
     * Note that if the user does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param username The username
     * @return A {@link CompletableFuture} with the corresponding optional user
     */
    default CompletableFuture<Optional<User>> getUserOptional(String username) {
        return getUser(username)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Gets the current user.
     *
     * @return A {@link CompletableFuture} with the user
     */
    CompletableFuture<User> getCurrentUser();

}
