package com.chavaillaz.jira.client;

import com.chavaillaz.jira.domain.*;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface IssueClient<T extends Issue> extends AutoCloseable {

    String URL_ISSUES = "issue/";
    String URL_ISSUE = "issue/{0}";
    String URL_ISSUE_DETAILS = "issue/{0}?expand=transitions,changelog";
    String URL_ISSUE_ASSIGNEE = "issue/{0}/assignee";
    String URL_ISSUE_TRANSITIONS = "issue/{0}/transitions";
    String URL_ISSUE_COMMENTS = "issue/{0}/comment";
    String URL_ISSUE_COMMENTS_SELECTION = "issue/{0}/comment?startAt={1}&maxResults={2}";
    String URL_ISSUE_COMMENT = "issue/{0}/comment/{1}";
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

    /**
     * Creates an issue.
     *
     * @param issue The issue to create
     * @return A {@link CompletableFuture} with issue identifiers
     */
    CompletableFuture<Identity> addIssue(T issue);

    /**
     * Returns a full representation of the issue for the given issue key.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} with the corresponding issue
     */
    CompletableFuture<T> getIssue(String issueKey);

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
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteIssue(String issueKey);

    /**
     * Assigns an issue to someone.
     *
     * @param issueKey The issue key
     * @param user     The user to assign the issue to
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> assignIssue(String issueKey, User user);

    /**
     * Removes the current user assigned to the issue.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} without content
     */
    default CompletableFuture<Void> unassignIssue(String issueKey) {
        return assignIssue(issueKey, null);
    }

    /**
     * Gets the possible transitions of an issue.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} with the possible transitions
     */
    CompletableFuture<Transitions> getTransitions(String issueKey);

    /**
     * Performs a transition for an issue.
     *
     * @param issueKey   The issue key
     * @param transition The transition to perform
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> doTransition(String issueKey, IssueTransition transition);

    /**
     * Gets the comments of an issue.
     * This uses a page offset of 0 and a number of results par page of 50.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} with the comments
     */
    default CompletableFuture<Comments> getComments(String issueKey) {
        return getComments(issueKey, 0, 50);
    }

    /**
     * Gets the comments of an issue with selection criteria.
     *
     * @param issueKey   The issue key
     * @param startAt    The page offset
     * @param maxResults The number of results per page
     * @return A {@link CompletableFuture} with the comments
     */
    CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults);

    /**
     * Gets a comment.
     *
     * @param issueKey The issue key
     * @param id       The comment identifier
     * @return A {@link CompletableFuture} with the comment
     */
    CompletableFuture<Comment> getComment(String issueKey, String id);

    /**
     * Adds a comment in an issue.
     *
     * @param issueKey The issue key
     * @param comment  The comment to add
     * @return A {@link CompletableFuture} with the comment
     */
    CompletableFuture<Comment> addComment(String issueKey, Comment comment);

    /**
     * Updates a comment.
     *
     * @param issueKey The issue key
     * @param comment  The comment to update
     * @return A {@link CompletableFuture} with the comment
     */
    CompletableFuture<Comment> updateComment(String issueKey, Comment comment);

    /**
     * Deletes a comment.
     *
     * @param issueKey The issue key
     * @param id       The comment identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteComment(String issueKey, String id);

    /**
     * Adds the vote of the current user to an issue.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> addVote(String issueKey);

    /**
     * Gets the votes of an issue.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} with the issue votes
     */
    CompletableFuture<Votes> getVotes(String issueKey);

    /**
     * Gets the watchers of an issue.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} with the issue watchers
     */
    CompletableFuture<Watchers> getWatchers(String issueKey);

    /**
     * Adds a user to the watchers of an issue.
     *
     * @param issueKey The issue key
     * @param username The username
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> addWatcher(String issueKey, String username);

    /**
     * Deletes a user from the watchers of an issue.
     *
     * @param issueKey The issue key
     * @param username The username
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteWatcher(String issueKey, String username);

    /**
     * Adds a work log to an issue.
     *
     * @param issueKey The issue key
     * @param workLog  The work log to add
     * @return A {@link CompletableFuture} with the work log
     */
    CompletableFuture<WorkLog> addWorkLog(String issueKey, WorkLog workLog);

    /**
     * Gets all work logs from an issue.
     *
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} with the work logs
     */
    CompletableFuture<WorkLogs> getWorkLogs(String issueKey);

    /**
     * Gets a work log.
     *
     * @param issueKey The issue key
     * @param id       The work log identifier
     * @return A {@link CompletableFuture} with the work log
     */
    CompletableFuture<WorkLog> getWorkLog(String issueKey, String id);

    /**
     * Updates a work log.
     *
     * @param issueKey The issue key
     * @param workLog  The work log containing only the fields to update
     * @return A {@link CompletableFuture} with the work log
     */
    CompletableFuture<WorkLog> updateWorkLog(String issueKey, WorkLog workLog);

    /**
     * Deletes a work log.
     *
     * @param issueKey The issue key
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
     * Gets an attachment content.
     *
     * @param url The complete URI locating the attachment content
     * @return A {@link CompletableFuture} with the attachment content as input stream
     */
    CompletableFuture<InputStream> getAttachmentContent(String url);

    /**
     * Adds an attachment to an issue.
     *
     * @param issueKey The issue key
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
     * @param issueKey The issue key
     * @return A {@link CompletableFuture} with the remote links
     */
    CompletableFuture<RemoteLinks> getRemoteLinks(String issueKey);

    /**
     * Gets a remote link.
     *
     * @param issueKey The issue key
     * @param id       The remote link identifier
     * @return A {@link CompletableFuture} with the remote link
     */
    CompletableFuture<RemoteLink> getRemoteLink(String issueKey, String id);

    /**
     * Adds a remote link to an issue.
     *
     * @param issueKey   The issue key
     * @param remoteLink The remote link
     * @return A {@link CompletableFuture} with the remote link identifiers
     */
    CompletableFuture<Identity> addRemoteLink(String issueKey, RemoteLink remoteLink);

    /**
     * Updates a remote link.
     *
     * @param issueKey   The issue key
     * @param remoteLink The remote link to update
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> updateRemoteLink(String issueKey, RemoteLink remoteLink);

    /**
     * Deletes a remote link.
     *
     * @param issueKey The issue key
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
