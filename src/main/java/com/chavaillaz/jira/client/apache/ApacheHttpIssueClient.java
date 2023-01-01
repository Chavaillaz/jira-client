package com.chavaillaz.jira.client.apache;

import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.domain.*;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import static com.chavaillaz.jira.client.apache.ApacheHttpUtils.multipartWithFiles;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.*;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

public class ApacheHttpIssueClient<T extends Issue> extends AbstractApacheHttpClient implements IssueClient<T> {

    protected final Class<T> issueType;

    /**
     * Creates a new {@link IssueClient} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     * @param issueType      The issue class type
     */
    public ApacheHttpIssueClient(CloseableHttpAsyncClient client, String baseUrl, String authentication, Class<T> issueType) {
        super(client, baseUrl, authentication);
        this.issueType = issueType;
    }

    @Override
    public CompletableFuture<Identity> addIssue(T issue) {
        return sendAsyncReturnDomain(requestBuilder(post(), URL_ISSUES)
                .setBody(serialize(issue), APPLICATION_JSON), Identity.class);
    }

    @Override
    public CompletableFuture<T> getIssue(String issueKey) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_DETAILS, issueKey), issueType);
    }

    @Override
    public CompletableFuture<Void> updateIssue(T issue) {
        return sendAsyncReturnVoid(requestBuilder(put(), URL_ISSUE, issue.getKey())
                .setBody(serialize(issue), APPLICATION_JSON));
    }

    @Override
    public CompletableFuture<Void> deleteIssue(String issueKey) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_ISSUE, issueKey));
    }

    @Override
    public CompletableFuture<Void> assignIssue(String issueKey, User user) {
        return sendAsyncReturnVoid(requestBuilder(put(), URL_ISSUE_ASSIGNEE, issueKey)
                .setBody(serialize(user), APPLICATION_JSON));
    }

    @Override
    public CompletableFuture<Transitions> getTransitions(String issueKey) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_TRANSITIONS, issueKey), Transitions.class);
    }

    @Override
    public CompletableFuture<Void> doTransition(String issueKey, IssueTransition transition) {
        return sendAsyncReturnVoid(requestBuilder(post(), URL_ISSUE_TRANSITIONS, issueKey)
                .setBody(serialize(transition), APPLICATION_JSON));
    }

    @Override
    public CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_COMMENTS_SELECTION, issueKey, startAt, maxResults), Comments.class);
    }

    @Override
    public CompletableFuture<Comment> getComment(String issueKey, String id) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_COMMENT, issueKey, id), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> addComment(String issueKey, Comment comment) {
        return sendAsyncReturnDomain(requestBuilder(post(), URL_ISSUE_COMMENTS, issueKey)
                .setBody(serialize(comment), APPLICATION_JSON), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> updateComment(String issueKey, Comment comment) {
        return sendAsyncReturnDomain(requestBuilder(put(), URL_ISSUE_COMMENT, issueKey, comment.getId())
                .setBody(serialize(comment), APPLICATION_JSON), Comment.class);
    }

    @Override
    public CompletableFuture<Void> deleteComment(String issueKey, String id) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_ISSUE_COMMENT, issueKey, id));
    }

    @Override
    public CompletableFuture<Void> addVote(String issueKey) {
        return sendAsyncReturnVoid(requestBuilder(post(), URL_ISSUE_VOTES, issueKey));
    }

    @Override
    public CompletableFuture<Votes> getVotes(String issueKey) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_VOTES, issueKey), Votes.class);
    }

    @Override
    public CompletableFuture<Watchers> getWatchers(String issueKey) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_WATCHERS, issueKey), Watchers.class);
    }

    @Override
    public CompletableFuture<Void> addWatcher(String issueKey, String username) {
        return sendAsyncReturnVoid(requestBuilder(post(), URL_ISSUE_WATCHERS, issueKey)
                .setBody(serialize(username), APPLICATION_JSON));
    }

    @Override
    public CompletableFuture<Void> deleteWatcher(String issueKey, String username) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_ISSUE_WATCHER, issueKey, username));
    }

    @Override
    public CompletableFuture<WorkLog> addWorkLog(String issueKey, WorkLog workLog) {
        return sendAsyncReturnDomain(requestBuilder(post(), URL_ISSUE_WORK_LOGS, issueKey)
                .setBody(serialize(workLog), APPLICATION_JSON), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLogs> getWorkLogs(String issueKey) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_WORK_LOGS, issueKey), WorkLogs.class);
    }

    @Override
    public CompletableFuture<WorkLog> getWorkLog(String issueKey, String id) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_WORK_LOG, issueKey, id), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLog> updateWorkLog(String issueKey, WorkLog workLog) {
        return sendAsyncReturnDomain(requestBuilder(put(), URL_ISSUE_WORK_LOG, issueKey, workLog.getId())
                .setBody(serialize(workLog), APPLICATION_JSON), WorkLog.class);
    }

    @Override
    public CompletableFuture<Void> deleteWorkLog(String issueKey, String id) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_ISSUE_WORK_LOG, issueKey, id));
    }

    @Override
    public CompletableFuture<Attachment> getAttachment(String id) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ATTACHMENT, id), Attachment.class);
    }

    @Override
    public CompletableFuture<InputStream> getAttachmentContent(String url) {
        return sendAsyncReturnStream(requestBuilder(get(), url));
    }

    @Override
    @SneakyThrows
    public CompletableFuture<Attachments> addAttachment(String issueKey, File... files) {
        return sendAsyncMultipartReturnDomain(requestBuilder(post(), URL_ISSUE_ATTACHMENTS, issueKey), multipartWithFiles(files), Attachments.class);
    }

    @Override
    public CompletableFuture<Void> deleteAttachment(String id) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_ATTACHMENT, id));
    }

    @Override
    public CompletableFuture<RemoteLinks> getRemoteLinks(String issueKey) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_REMOTE_LINKS, issueKey), RemoteLinks.class);
    }

    @Override
    public CompletableFuture<RemoteLink> getRemoteLink(String issueKey, String id) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_REMOTE_LINK, issueKey, id), RemoteLink.class);
    }

    @Override
    public CompletableFuture<Identity> addRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsyncReturnDomain(requestBuilder(post(), URL_ISSUE_REMOTE_LINKS, issueKey)
                .setBody(serialize(remoteLink), APPLICATION_JSON), Identity.class);
    }

    @Override
    public CompletableFuture<Void> updateRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsyncReturnVoid(requestBuilder(put(), URL_ISSUE_REMOTE_LINK, issueKey, remoteLink.getId())
                .setBody(serialize(remoteLink), APPLICATION_JSON));
    }

    @Override
    public CompletableFuture<Void> deleteRemoteLink(String issueKey, String id) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_ISSUE_REMOTE_LINK, issueKey, id));
    }

    @Override
    public CompletableFuture<Link> getIssueLink(String id) {
        return sendAsyncReturnDomain(requestBuilder(get(), URL_ISSUE_LINK, id), Link.class);
    }

    @Override
    public CompletableFuture<Void> addIssueLink(Link link) {
        return sendAsyncReturnVoid(requestBuilder(post(), URL_ISSUE_LINKS)
                .setBody(serialize(link), APPLICATION_JSON));
    }

    @Override
    public CompletableFuture<Void> deleteIssueLink(String id) {
        return sendAsyncReturnVoid(requestBuilder(delete(), URL_ISSUE_LINK, id));
    }

}
