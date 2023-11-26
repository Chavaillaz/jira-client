package com.chavaillaz.client.jira.vertx;

import static com.chavaillaz.client.common.vertx.VertxUtils.multipartWithFiles;
import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN;
import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN_DISABLED;
import static io.vertx.core.http.HttpMethod.DELETE;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;
import static io.vertx.core.http.HttpMethod.PUT;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.IssueClient;
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
import io.vertx.ext.web.client.WebClient;

public class VertxHttpIssueClient<T extends Issue> extends AbstractVertxHttpClient implements IssueClient<T> {

    protected final Class<T> issueType;

    /**
     * Creates a new {@link IssueClient} using Vert.x client.
     *
     * @param client         The Vert.x client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     * @param issueType      The issue class type
     */
    public VertxHttpIssueClient(WebClient client, String baseUrl, Authentication authentication, Class<T> issueType) {
        super(client, baseUrl, authentication);
        this.issueType = issueType;
    }

    @Override
    public CompletableFuture<Identity> addIssue(T issue) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_CREATION).sendBuffer(body(issue)), Identity.class);
    }

    @Override
    public CompletableFuture<T> getIssue(String issueKey, IssueExpand... expandFlags) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_SELECTION, issueKey, IssueExpand.getParameters(expandFlags)).send(), issueType);
    }

    @Override
    public CompletableFuture<Void> updateIssue(T issue) {
        return handleAsync(requestBuilder(PUT, URL_ISSUE_ACTION, issue.getKey()).sendBuffer(body(issue)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssue(String issueKey) {
        return handleAsync(requestBuilder(DELETE, URL_ISSUE_ACTION, issueKey).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> assignIssue(String issueKey, User user) {
        return handleAsync(requestBuilder(PUT, URL_ISSUE_ASSIGNEE, issueKey).sendBuffer(body(user)), Void.class);
    }

    @Override
    public CompletableFuture<Transitions> getTransitions(String issueKey) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_TRANSITIONS, issueKey).send(), Transitions.class);
    }

    @Override
    public CompletableFuture<Void> doTransition(String issueKey, IssueTransition transition) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_TRANSITIONS, issueKey).sendBuffer(body(transition)), Void.class);
    }

    @Override
    public CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults, CommentExpand... expandFlags) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_COMMENTS_SELECTION, issueKey, startAt, maxResults, CommentExpand.getParameters(expandFlags)).send(), Comments.class);
    }

    @Override
    public CompletableFuture<Comment> getComment(String issueKey, String id, CommentExpand... expandFlags) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_COMMENT_SELECTION, issueKey, id, CommentExpand.getParameters(expandFlags)).send(), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> addComment(String issueKey, Comment comment) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_COMMENT_CREATION, issueKey).sendBuffer(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> updateComment(String issueKey, Comment comment) {
        return handleAsync(requestBuilder(PUT, URL_ISSUE_COMMENT_ACTION, issueKey, comment.getId()).sendBuffer(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Void> deleteComment(String issueKey, String id) {
        return handleAsync(requestBuilder(DELETE, URL_ISSUE_COMMENT_ACTION, issueKey, id).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> addVote(String issueKey) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_VOTES, issueKey).send(), Void.class);
    }

    @Override
    public CompletableFuture<Votes> getVotes(String issueKey) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_VOTES, issueKey).send(), Votes.class);
    }

    @Override
    public CompletableFuture<Watchers> getWatchers(String issueKey) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_WATCHERS, issueKey).send(), Watchers.class);
    }

    @Override
    public CompletableFuture<Void> addWatcher(String issueKey, String username) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_WATCHERS, issueKey).sendBuffer(body(username)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteWatcher(String issueKey, String username) {
        return handleAsync(requestBuilder(DELETE, URL_ISSUE_WATCHER, issueKey, username).send(), Void.class);
    }

    @Override
    public CompletableFuture<WorkLog> addWorkLog(String issueKey, WorkLog workLog) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_WORK_LOGS, issueKey).sendBuffer(body(workLog)), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLogs> getWorkLogs(String issueKey) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_WORK_LOGS, issueKey).send(), WorkLogs.class);
    }

    @Override
    public CompletableFuture<WorkLog> getWorkLog(String issueKey, String id) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_WORK_LOG, issueKey, id).send(), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLog> updateWorkLog(String issueKey, WorkLog workLog) {
        return handleAsync(requestBuilder(PUT, URL_ISSUE_WORK_LOG, issueKey, workLog.getId()).sendBuffer(body(workLog)), WorkLog.class);
    }

    @Override
    public CompletableFuture<Void> deleteWorkLog(String issueKey, String id) {
        return handleAsync(requestBuilder(DELETE, URL_ISSUE_WORK_LOG, issueKey, id).send(), Void.class);
    }

    @Override
    public CompletableFuture<Attachment> getAttachment(String id) {
        return handleAsync(requestBuilder(GET, URL_ATTACHMENT, id).send(), Attachment.class);
    }

    @Override
    public CompletableFuture<InputStream> getAttachmentContent(String url) {
        return handleAsync(requestBuilder(GET, url).send());
    }

    @Override
    public CompletableFuture<Attachments> addAttachment(String issueKey, File... files) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_ATTACHMENTS, issueKey)
                        .putHeader(HEADER_CONTENT_TYPE, "multipart/form-data")
                        .putHeader(HEADER_ATLASSIAN_TOKEN, HEADER_ATLASSIAN_TOKEN_DISABLED)
                        .sendMultipartForm(multipartWithFiles(files)),
                Attachments.class);
    }

    @Override
    public CompletableFuture<Void> deleteAttachment(String id) {
        return handleAsync(requestBuilder(DELETE, URL_ATTACHMENT, id).send(), Void.class);
    }

    @Override
    public CompletableFuture<RemoteLinks> getRemoteLinks(String issueKey) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_REMOTE_LINKS, issueKey).send(), RemoteLinks.class);
    }

    @Override
    public CompletableFuture<RemoteLink> getRemoteLink(String issueKey, String id) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_REMOTE_LINK, issueKey, id).send(), RemoteLink.class);
    }

    @Override
    public CompletableFuture<Identity> addRemoteLink(String issueKey, RemoteLink remoteLink) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_REMOTE_LINKS, issueKey).sendBuffer(body(remoteLink)), Identity.class);
    }

    @Override
    public CompletableFuture<Void> updateRemoteLink(String issueKey, RemoteLink remoteLink) {
        return handleAsync(requestBuilder(PUT, URL_ISSUE_REMOTE_LINK, issueKey, remoteLink.getId()).sendBuffer(body(remoteLink)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteRemoteLink(String issueKey, String id) {
        return handleAsync(requestBuilder(DELETE, URL_ISSUE_REMOTE_LINK, issueKey, id).send(), Void.class);
    }

    @Override
    public CompletableFuture<Link> getIssueLink(String id) {
        return handleAsync(requestBuilder(GET, URL_ISSUE_LINK, id).send(), Link.class);
    }

    @Override
    public CompletableFuture<Void> addIssueLink(Link link) {
        return handleAsync(requestBuilder(POST, URL_ISSUE_LINKS).sendBuffer(body(link)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssueLink(String id) {
        return handleAsync(requestBuilder(DELETE, URL_ISSUE_LINK, id).send(), Void.class);
    }

}
