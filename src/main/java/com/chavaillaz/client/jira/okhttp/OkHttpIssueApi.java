package com.chavaillaz.client.jira.okhttp;

import static com.chavaillaz.client.common.okhttp.OkHttpUtils.multipartWithFiles;
import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN;
import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN_DISABLED;
import static java.lang.String.join;

import java.io.File;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.api.IssueApi;
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
import okhttp3.OkHttpClient;

public class OkHttpIssueApi<T extends Issue> extends AbstractOkHttpClient implements IssueApi<T> {

    protected final Class<T> issueType;

    /**
     * Creates a new {@link IssueApi} using OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     * @param issueType      The issue class type
     */
    public OkHttpIssueApi(OkHttpClient client, String baseUrl, Authentication authentication, Class<T> issueType) {
        super(client, baseUrl, authentication);
        this.issueType = issueType;
    }

    @Override
    public CompletableFuture<Identity> addIssue(T issue) {
        return sendAsync(requestBuilder(URL_ISSUE_CREATION).post(body(issue)), Identity.class);
    }

    @Override
    public CompletableFuture<T> getIssue(String issueKey, Set<IssueExpand> expandFlags, Set<String> fields) {
        return sendAsync(requestBuilder(URL_ISSUE_SELECTION, issueKey, IssueExpand.asParameter(expandFlags), join(",", fields)).get(), issueType);
    }

    @Override
    public CompletableFuture<Void> updateIssue(T issue) {
        return sendAsync(requestBuilder(URL_ISSUE_ACTION, issue.getKey()).put(body(issue)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssue(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_ACTION, issueKey).delete(), Void.class);
    }

    @Override
    public CompletableFuture<Void> assignIssue(String issueKey, User user) {
        return sendAsync(requestBuilder(URL_ISSUE_ASSIGNEE, issueKey).put(body(user)), Void.class);
    }

    @Override
    public CompletableFuture<Transitions> getTransitions(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_TRANSITIONS, issueKey).get(), Transitions.class);
    }

    @Override
    public CompletableFuture<Void> doTransition(String issueKey, IssueTransition transition) {
        return sendAsync(requestBuilder(URL_ISSUE_TRANSITIONS, issueKey).post(body(transition)), Void.class);
    }

    @Override
    public CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults, Set<CommentExpand> expandFlags) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENTS_SELECTION, issueKey, startAt, maxResults, CommentExpand.asParameter(expandFlags)).get(), Comments.class);
    }

    @Override
    public CompletableFuture<Comment> getComment(String issueKey, String id, Set<CommentExpand> expandFlags) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_SELECTION, issueKey, id, CommentExpand.asParameter(expandFlags)).get(), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> addComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_CREATION, issueKey).post(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> updateComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_ACTION, issueKey, comment.getId()).put(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Void> deleteComment(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_ACTION, issueKey, id).delete(), Void.class);
    }

    @Override
    public CompletableFuture<Void> addVote(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_VOTES, issueKey).post(body()), Void.class);
    }

    @Override
    public CompletableFuture<Votes> getVotes(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_VOTES, issueKey).get(), Votes.class);
    }

    @Override
    public CompletableFuture<Watchers> getWatchers(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_WATCHERS, issueKey).get(), Watchers.class);
    }

    @Override
    public CompletableFuture<Void> addWatcher(String issueKey, String username) {
        return sendAsync(requestBuilder(URL_ISSUE_WATCHERS, issueKey).post(body(username)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteWatcher(String issueKey, String username) {
        return sendAsync(requestBuilder(URL_ISSUE_WATCHER, issueKey, username).delete(), Void.class);
    }

    @Override
    public CompletableFuture<WorkLog> addWorkLog(String issueKey, WorkLog workLog) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOGS, issueKey).post(body(workLog)), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLogs> getWorkLogs(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOGS, issueKey).get(), WorkLogs.class);
    }

    @Override
    public CompletableFuture<WorkLog> getWorkLog(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOG, issueKey, id).get(), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLog> updateWorkLog(String issueKey, WorkLog workLog) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOG, issueKey, workLog.getId()).put(body(workLog)), WorkLog.class);
    }

    @Override
    public CompletableFuture<Void> deleteWorkLog(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOG, issueKey, id).delete(), Void.class);
    }

    @Override
    public CompletableFuture<Attachment> getAttachment(String id) {
        return sendAsync(requestBuilder(URL_ATTACHMENT, id).get(), Attachment.class);
    }

    @Override
    public CompletableFuture<InputStream> getAttachmentContent(String url) {
        return sendAsync(requestBuilder(url).get());
    }

    @Override
    public CompletableFuture<Attachments> addAttachment(String issueKey, File... files) {
        return sendAsync(requestBuilder(URL_ISSUE_ATTACHMENTS, issueKey)
                        .header(HEADER_ATLASSIAN_TOKEN, HEADER_ATLASSIAN_TOKEN_DISABLED)
                        .post(multipartWithFiles(files)),
                Attachments.class);
    }

    @Override
    public CompletableFuture<Void> deleteAttachment(String id) {
        return sendAsync(requestBuilder(URL_ATTACHMENT, id).delete(), Void.class);
    }

    @Override
    public CompletableFuture<RemoteLinks> getRemoteLinks(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINKS, issueKey).get(), RemoteLinks.class);
    }

    @Override
    public CompletableFuture<RemoteLink> getRemoteLink(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, id).get(), RemoteLink.class);
    }

    @Override
    public CompletableFuture<Identity> addRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINKS, issueKey).post(body(remoteLink)), Identity.class);
    }

    @Override
    public CompletableFuture<Void> updateRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, remoteLink.getId()).put(body(remoteLink)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteRemoteLink(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, id).delete(), Void.class);
    }

    @Override
    public CompletableFuture<Link> getIssueLink(String id) {
        return sendAsync(requestBuilder(URL_ISSUE_LINK, id).get(), Link.class);
    }

    @Override
    public CompletableFuture<Void> addIssueLink(Link link) {
        return sendAsync(requestBuilder(URL_ISSUE_LINKS).post(body(link)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssueLink(String id) {
        return sendAsync(requestBuilder(URL_ISSUE_LINK, id).delete(), Void.class);
    }

}
