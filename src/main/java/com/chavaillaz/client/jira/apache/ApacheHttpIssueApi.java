package com.chavaillaz.client.jira.apache;

import static com.chavaillaz.client.common.apache.ApacheHttpUtils.multipartWithFiles;
import static java.lang.String.join;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.delete;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.put;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

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
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

public class ApacheHttpIssueApi<T extends Issue> extends AbstractApacheHttpClient implements IssueApi<T> {

    protected final Class<T> issueType;

    /**
     * Creates a new {@link IssueApi} using Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     * @param issueType      The issue class type
     */
    public ApacheHttpIssueApi(CloseableHttpAsyncClient client, String baseUrl, Authentication authentication, Class<T> issueType) {
        super(client, baseUrl, authentication);
        this.issueType = issueType;
    }

    @Override
    public CompletableFuture<Identity> addIssue(T issue) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_CREATION)
                .setBody(serialize(issue), APPLICATION_JSON), Identity.class);
    }

    @Override
    public CompletableFuture<T> getIssue(String issueKey, Set<IssueExpand> expandFlags, Set<String> fields) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_SELECTION, issueKey, IssueExpand.asParameter(expandFlags), join(",", fields)), issueType);
    }

    @Override
    public CompletableFuture<Void> updateIssue(T issue) {
        return sendAsync(requestBuilder(put(), URL_ISSUE_ACTION, issue.getKey())
                .setBody(serialize(issue), APPLICATION_JSON), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssue(String issueKey) {
        return sendAsync(requestBuilder(delete(), URL_ISSUE_ACTION, issueKey), Void.class);
    }

    @Override
    public CompletableFuture<Void> assignIssue(String issueKey, User user) {
        return sendAsync(requestBuilder(put(), URL_ISSUE_ASSIGNEE, issueKey)
                .setBody(serialize(user), APPLICATION_JSON), Void.class);
    }

    @Override
    public CompletableFuture<Transitions> getTransitions(String issueKey) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_TRANSITIONS, issueKey), Transitions.class);
    }

    @Override
    public CompletableFuture<Void> doTransition(String issueKey, IssueTransition transition) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_TRANSITIONS, issueKey)
                .setBody(serialize(transition), APPLICATION_JSON), Void.class);
    }

    @Override
    public CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults, Set<CommentExpand> expandFlags) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_COMMENTS_SELECTION, issueKey, startAt, maxResults, CommentExpand.asParameter(expandFlags)), Comments.class);
    }

    @Override
    public CompletableFuture<Comment> getComment(String issueKey, String id, Set<CommentExpand> expandFlags) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_COMMENT_SELECTION, issueKey, id, CommentExpand.asParameter(expandFlags)), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> addComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_COMMENT_CREATION, issueKey)
                .setBody(serialize(comment), APPLICATION_JSON), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> updateComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(put(), URL_ISSUE_COMMENT_ACTION, issueKey, comment.getId())
                .setBody(serialize(comment), APPLICATION_JSON), Comment.class);
    }

    @Override
    public CompletableFuture<Void> deleteComment(String issueKey, String id) {
        return sendAsync(requestBuilder(delete(), URL_ISSUE_COMMENT_ACTION, issueKey, id), Void.class);
    }

    @Override
    public CompletableFuture<Void> addVote(String issueKey) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_VOTES, issueKey), Void.class);
    }

    @Override
    public CompletableFuture<Votes> getVotes(String issueKey) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_VOTES, issueKey), Votes.class);
    }

    @Override
    public CompletableFuture<Watchers> getWatchers(String issueKey) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_WATCHERS, issueKey), Watchers.class);
    }

    @Override
    public CompletableFuture<Void> addWatcher(String issueKey, String username) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_WATCHERS, issueKey)
                .setBody(serialize(username), APPLICATION_JSON), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteWatcher(String issueKey, String username) {
        return sendAsync(requestBuilder(delete(), URL_ISSUE_WATCHER, issueKey, username), Void.class);
    }

    @Override
    public CompletableFuture<WorkLog> addWorkLog(String issueKey, WorkLog workLog) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_WORK_LOGS, issueKey)
                .setBody(serialize(workLog), APPLICATION_JSON), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLogs> getWorkLogs(String issueKey) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_WORK_LOGS, issueKey), WorkLogs.class);
    }

    @Override
    public CompletableFuture<WorkLog> getWorkLog(String issueKey, String id) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_WORK_LOG, issueKey, id), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLog> updateWorkLog(String issueKey, WorkLog workLog) {
        return sendAsync(requestBuilder(put(), URL_ISSUE_WORK_LOG, issueKey, workLog.getId())
                .setBody(serialize(workLog), APPLICATION_JSON), WorkLog.class);
    }

    @Override
    public CompletableFuture<Void> deleteWorkLog(String issueKey, String id) {
        return sendAsync(requestBuilder(delete(), URL_ISSUE_WORK_LOG, issueKey, id), Void.class);
    }

    @Override
    public CompletableFuture<Attachment> getAttachment(String id) {
        return sendAsync(requestBuilder(get(), URL_ATTACHMENT, id), Attachment.class);
    }

    @Override
    public CompletableFuture<InputStream> getAttachmentContent(String url) {
        return sendAsync(requestBuilder(get(), url));
    }

    @Override
    public CompletableFuture<Attachments> addAttachment(String issueKey, File... files) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_ATTACHMENTS, issueKey), multipartWithFiles(files), Attachments.class);
    }

    @Override
    public CompletableFuture<Void> deleteAttachment(String id) {
        return sendAsync(requestBuilder(delete(), URL_ATTACHMENT, id), Void.class);
    }

    @Override
    public CompletableFuture<RemoteLinks> getRemoteLinks(String issueKey) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_REMOTE_LINKS, issueKey), RemoteLinks.class);
    }

    @Override
    public CompletableFuture<RemoteLink> getRemoteLink(String issueKey, String id) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_REMOTE_LINK, issueKey, id), RemoteLink.class);
    }

    @Override
    public CompletableFuture<Identity> addRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_REMOTE_LINKS, issueKey)
                .setBody(serialize(remoteLink), APPLICATION_JSON), Identity.class);
    }

    @Override
    public CompletableFuture<Void> updateRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsync(requestBuilder(put(), URL_ISSUE_REMOTE_LINK, issueKey, remoteLink.getId())
                .setBody(serialize(remoteLink), APPLICATION_JSON), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteRemoteLink(String issueKey, String id) {
        return sendAsync(requestBuilder(delete(), URL_ISSUE_REMOTE_LINK, issueKey, id), Void.class);
    }

    @Override
    public CompletableFuture<Link> getIssueLink(String id) {
        return sendAsync(requestBuilder(get(), URL_ISSUE_LINK, id), Link.class);
    }

    @Override
    public CompletableFuture<Void> addIssueLink(Link link) {
        return sendAsync(requestBuilder(post(), URL_ISSUE_LINKS)
                .setBody(serialize(link), APPLICATION_JSON), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssueLink(String id) {
        return sendAsync(requestBuilder(delete(), URL_ISSUE_LINK, id), Void.class);
    }

}
