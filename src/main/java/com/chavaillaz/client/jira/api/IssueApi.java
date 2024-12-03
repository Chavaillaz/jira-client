package com.chavaillaz.client.jira.api;

import static com.chavaillaz.client.jira.api.expand.IssueExpand.CHANGELOG;
import static com.chavaillaz.client.jira.api.expand.IssueExpand.TRANSITIONS;
import static java.util.Collections.emptySet;
import static java.util.Optional.empty;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jira.api.expand.CommentExpand;
import com.chavaillaz.client.jira.api.expand.IssueExpand;
import com.chavaillaz.client.jira.domain.Attachment;
import com.chavaillaz.client.jira.domain.Attachments;
import com.chavaillaz.client.jira.domain.Comment;
import com.chavaillaz.client.jira.domain.Comments;
import com.chavaillaz.client.jira.domain.Identity;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.domain.IssueTransition;
import com.chavaillaz.client.jira.domain.Link;
import com.chavaillaz.client.jira.domain.RemoteLink;
import com.chavaillaz.client.jira.domain.RemoteLinks;
import com.chavaillaz.client.jira.domain.Transitions;
import com.chavaillaz.client.jira.domain.User;
import com.chavaillaz.client.jira.domain.Votes;
import com.chavaillaz.client.jira.domain.Watchers;
import com.chavaillaz.client.jira.domain.WorkLog;
import com.chavaillaz.client.jira.domain.WorkLogs;

public interface IssueApi<T extends Issue> extends AutoCloseable {

    String URL_ISSUE_CREATION = "issue/";
    String URL_ISSUE_ACTION = "issue/{0}";
    String URL_ISSUE_SELECTION = "issue/{0}?expand={1}&fields={2}";
    String URL_ISSUE_ASSIGNEE = "issue/{0}/assignee";
    String URL_ISSUE_TRANSITIONS = "issue/{0}/transitions";
    String URL_ISSUE_COMMENTS_SELECTION = "issue/{0}/comment?startAt={1}&maxResults={2}&expand={3}";
    String URL_ISSUE_COMMENT_CREATION = "issue/{0}/comment";
    String URL_ISSUE_COMMENT_ACTION = "issue/{0}/comment/{1}";
    String URL_ISSUE_COMMENT_SELECTION = "issue/{0}/comment/{1}?expand={2}";
    String URL_ISSUE_VOTES = "issue/{0}/votes";
    String URL_ISSUE_WATCHERS = "issue/{0}/watchers";
    String URL_ISSUE_WATCHER = "issue/{0}/watchers?username={1}";
    String URL_ISSUE_WORK_LOGS = "issue/{0}/worklog";
    String URL_ISSUE_WORK_LOG = "issue/{0}/worklog/{1}";
    String URL_ISSUE_REMOTE_LINKS = "issue/{0}/remotelink";
    String URL_ISSUE_REMOTE_LINK = "issue/{0}/remotelink/{1}";
    String URL_ISSUE_ATTACHMENTS = "issue/{0}/attachments";
    String URL_ATTACHMENT = "attachment/{0}";
    String URL_ISSUE_LINKS = "issueLink/";
    String URL_ISSUE_LINK = "issueLink/{0}";

    Set<String> ALL_FIELDS = Set.of("*all");

    /**
     * Creates an issue.
     *
     * @param issue The issue to create
     * @return A {@link CompletableFuture} with issue identifiers
     */
    CompletableFuture<Identity> addIssue(T issue);

    /**
     * Returns a full representation of the issue identified by the given key.
     * By default, the issue will be expanded with its changelog and possible transitions.
     * Note that if the issue does not exist, the {@link CompletableFuture} will complete exceptionally.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the corresponding issue
     */
    default CompletableFuture<T> getIssue(String issueKey) {
        return getIssue(issueKey, Set.of(CHANGELOG, TRANSITIONS));
    }

    /**
     * Returns a full representation of the issue identified by the given key.
     * Note that if the issue does not exist, the {@link CompletableFuture} will complete exceptionally.
     *
     * @param issueKey    The key identifying the issue
     * @param expandFlags The optional flags to expand values returned
     * @return A {@link CompletableFuture} with the corresponding issue
     */
    default CompletableFuture<T> getIssue(String issueKey, Set<IssueExpand> expandFlags) {
        return getIssue(issueKey, expandFlags, ALL_FIELDS);
    }

    /**
     * Returns a representation with the given fields of the issue identified by the given key.
     * Note that if the issue does not exist, the {@link CompletableFuture} will complete exceptionally.
     *
     * @param issueKey    The key identifying the issue
     * @param expandFlags The optional flags to expand values returned
     * @param fields      The list of fields to be present in the issue retrieved
     * @return A {@link CompletableFuture} with the corresponding issue
     */
    CompletableFuture<T> getIssue(String issueKey, Set<IssueExpand> expandFlags, Set<String> fields);

    /**
     * Returns a full representation of the issue identified by the given key.
     * By default, the issue will be expanded with its changelog and possible transitions.
     * Note that if the issue does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the corresponding optional issue
     */
    default CompletableFuture<Optional<T>> getIssueOptional(String issueKey) {
        return getIssue(issueKey)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Returns a full representation of the issue identified by the given key.
     * Note that if the issue does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param issueKey    The key identifying the issue
     * @param expandFlags The optional flags to expand values returned
     * @return A {@link CompletableFuture} with the corresponding optional issue
     */
    default CompletableFuture<Optional<T>> getIssueOptional(String issueKey, Set<IssueExpand> expandFlags) {
        return getIssue(issueKey, expandFlags)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Returns a representation with the given fields of the issue identified by the given key.
     * Note that if the issue does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param issueKey    The key identifying the issue
     * @param expandFlags The optional flags to expand values returned
     * @param fields      The list of fields to be present in the issue retrieved
     * @return A {@link CompletableFuture} with the corresponding issue
     */
    default CompletableFuture<Optional<T>> getIssueOptional(String issueKey, Set<IssueExpand> expandFlags, Set<String> fields) {
        return getIssue(issueKey, expandFlags, fields)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Updates fields of an issue.
     *
     * @param issue The issue containing only the fields to update
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> updateIssue(T issue);

    /**
     * Deletes an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteIssue(String issueKey);

    /**
     * Assigns an issue to someone.
     *
     * @param issueKey The key identifying the issue
     * @param user     The user to assign the issue to
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> assignIssue(String issueKey, User user);

    /**
     * Removes the current user assigned to the issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} without content
     */
    default CompletableFuture<Void> unassignIssue(String issueKey) {
        return assignIssue(issueKey, null);
    }

    /**
     * Gets the possible transitions of an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the possible transitions
     */
    CompletableFuture<Transitions> getTransitions(String issueKey);

    /**
     * Performs a transition for an issue.
     *
     * @param issueKey   The key identifying the issue
     * @param transition The transition to perform
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> doTransition(String issueKey, IssueTransition<?> transition);

    /**
     * Gets the comments of an issue.
     * This uses a page offset of 0 and a number of results par page of 50.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the comments
     */
    default CompletableFuture<Comments> getComments(String issueKey) {
        return getComments(issueKey, 0, 50);
    }

    /**
     * Gets the comments of an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the comments
     */
    default CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults) {
        return getComments(issueKey, startAt, maxResults, emptySet());
    }

    /**
     * Gets the comments of an issue.
     *
     * @param issueKey    The key identifying the issue
     * @param startAt     The page offset
     * @param maxResults  The number of results per page
     * @param expandFlags The optional flags to expand values returned
     * @return A {@link CompletableFuture} with the comments
     */
    CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults, Set<CommentExpand> expandFlags);

    /**
     * Gets a comment.
     *
     * @param issueKey The key identifying the issue
     * @param id       The comment identifier
     * @return A {@link CompletableFuture} with the comment
     */
    default CompletableFuture<Comment> getComment(String issueKey, String id) {
        return getComment(issueKey, id, emptySet());
    }

    /**
     * Gets a comment.
     *
     * @param issueKey    The key identifying the issue
     * @param id          The comment identifier
     * @param expandFlags The optional flags to expand values returned
     * @return A {@link CompletableFuture} with the comment
     */
    CompletableFuture<Comment> getComment(String issueKey, String id, Set<CommentExpand> expandFlags);

    /**
     * Gets a comment.
     * Note that if the comment does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param issueKey The key identifying the issue
     * @param id       The comment identifier
     * @return A {@link CompletableFuture} with the corresponding optional comment
     */
    default CompletableFuture<Optional<Comment>> getCommentOptional(String issueKey, String id) {
        return getComment(issueKey, id)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Gets a comment.
     * Note that if the comment does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param issueKey    The key identifying the issue
     * @param id          The comment identifier
     * @param expandFlags The optional flags to expand values returned
     * @return A {@link CompletableFuture} with the corresponding optional comment
     */
    default CompletableFuture<Optional<Comment>> getCommentOptional(String issueKey, String id, Set<CommentExpand> expandFlags) {
        return getComment(issueKey, id, expandFlags)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Adds a comment in an issue.
     *
     * @param issueKey The key identifying the issue
     * @param comment  The comment to add
     * @return A {@link CompletableFuture} with the comment
     */
    CompletableFuture<Comment> addComment(String issueKey, Comment comment);

    /**
     * Updates a comment.
     *
     * @param issueKey The key identifying the issue
     * @param comment  The comment to update
     * @return A {@link CompletableFuture} with the comment
     */
    CompletableFuture<Comment> updateComment(String issueKey, Comment comment);

    /**
     * Deletes a comment.
     *
     * @param issueKey The key identifying the issue
     * @param id       The comment identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteComment(String issueKey, String id);

    /**
     * Adds the vote of the current user to an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> addVote(String issueKey);

    /**
     * Gets the votes of an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the issue votes
     */
    CompletableFuture<Votes> getVotes(String issueKey);

    /**
     * Gets the watchers of an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the issue watchers
     */
    CompletableFuture<Watchers> getWatchers(String issueKey);

    /**
     * Adds a user to the watchers of an issue.
     *
     * @param issueKey The key identifying the issue
     * @param username The username
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> addWatcher(String issueKey, String username);

    /**
     * Deletes a user from the watchers of an issue.
     *
     * @param issueKey The key identifying the issue
     * @param username The username
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteWatcher(String issueKey, String username);

    /**
     * Adds a work log to an issue.
     *
     * @param issueKey The key identifying the issue
     * @param workLog  The work log to add
     * @return A {@link CompletableFuture} with the work log
     */
    CompletableFuture<WorkLog> addWorkLog(String issueKey, WorkLog workLog);

    /**
     * Gets all work logs from an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the work logs
     */
    CompletableFuture<WorkLogs> getWorkLogs(String issueKey);

    /**
     * Gets a work log.
     *
     * @param issueKey The key identifying the issue
     * @param id       The work log identifier
     * @return A {@link CompletableFuture} with the work log
     */
    CompletableFuture<WorkLog> getWorkLog(String issueKey, String id);

    /**
     * Gets a work log.
     * Note that if the work log does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param issueKey The key identifying the issue
     * @param id       The work log identifier
     * @return A {@link CompletableFuture} with the corresponding optional work log
     */
    default CompletableFuture<Optional<WorkLog>> getWorkLogOptional(String issueKey, String id) {
        return getWorkLog(issueKey, id)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Updates a work log.
     *
     * @param issueKey The key identifying the issue
     * @param workLog  The work log containing only the fields to update
     * @return A {@link CompletableFuture} with the work log
     */
    CompletableFuture<WorkLog> updateWorkLog(String issueKey, WorkLog workLog);

    /**
     * Deletes a work log.
     *
     * @param issueKey The key identifying the issue
     * @param id       The work log identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteWorkLog(String issueKey, String id);

    /**
     * Gets an attachment.
     *
     * @param id The attachment identifier
     * @return A {@link CompletableFuture} with the attachment
     */
    CompletableFuture<Attachment> getAttachment(String id);

    /**
     * Gets an attachment.
     * Note that if the attachment does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param id The attachment identifier
     * @return A {@link CompletableFuture} with the corresponding optional attachment
     */
    default CompletableFuture<Optional<Attachment>> getAttachmentOptional(String id) {
        return getAttachment(id)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Gets an attachment content.
     *
     * @param url The complete URI locating the attachment content
     * @return A {@link CompletableFuture} with the attachment content as input stream
     */
    CompletableFuture<InputStream> getAttachmentContent(String url);

    /**
     * Adds an attachment to an issue.
     *
     * @param issueKey The key identifying the issue
     * @param files    The attachments to add
     * @return A {@link CompletableFuture} with the attachment
     */
    CompletableFuture<Attachments> addAttachment(String issueKey, File... files);

    /**
     * Deletes an attachment.
     *
     * @param id The attachment identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteAttachment(String id);

    /**
     * Gets the remote links of an issue.
     *
     * @param issueKey The key identifying the issue
     * @return A {@link CompletableFuture} with the remote links
     */
    CompletableFuture<RemoteLinks> getRemoteLinks(String issueKey);

    /**
     * Gets a remote link.
     *
     * @param issueKey The key identifying the issue
     * @param id       The remote link identifier
     * @return A {@link CompletableFuture} with the remote link
     */
    CompletableFuture<RemoteLink> getRemoteLink(String issueKey, String id);

    /**
     * Gets a remote link.
     * Note that if the remote link does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param issueKey The key identifying the issue
     * @param id       The remote link identifier
     * @return A {@link CompletableFuture} with the corresponding optional remote link
     */
    default CompletableFuture<Optional<RemoteLink>> getRemoteLinkOptional(String issueKey, String id) {
        return getRemoteLink(issueKey, id)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Adds a remote link to an issue.
     *
     * @param issueKey   The key identifying the issue
     * @param remoteLink The remote link
     * @return A {@link CompletableFuture} with the remote link identifiers
     */
    CompletableFuture<Identity> addRemoteLink(String issueKey, RemoteLink remoteLink);

    /**
     * Updates a remote link.
     *
     * @param issueKey   The key identifying the issue
     * @param remoteLink The remote link to update
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> updateRemoteLink(String issueKey, RemoteLink remoteLink);

    /**
     * Deletes a remote link.
     *
     * @param issueKey The key identifying the issue
     * @param id       The remote link identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteRemoteLink(String issueKey, String id);

    /**
     * Gets an issue link.
     *
     * @param id The issue link identifier
     * @return A {@link CompletableFuture} with the issue link
     */
    CompletableFuture<Link> getIssueLink(String id);

    /**
     * Gets an issue link.
     * Note that if the issue link does not exist, an {@link Optional#empty()} will be returned.
     *
     * @param id The issue link identifier
     * @return A {@link CompletableFuture} with the corresponding optional issue link
     */
    default CompletableFuture<Optional<Link>> getIssueLinkOptional(String id) {
        return getIssueLink(id)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Adds an issue link.
     *
     * @param link The issue link
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> addIssueLink(Link link);

    /**
     * Deletes an issue link.
     *
     * @param id The issue link identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteIssueLink(String id);

}
