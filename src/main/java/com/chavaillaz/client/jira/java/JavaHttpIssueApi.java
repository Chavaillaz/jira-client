package com.chavaillaz.client.jira.java;

import static com.chavaillaz.client.common.java.JavaHttpUtils.mimeMultipartData;
import static com.chavaillaz.client.common.java.JavaHttpUtils.multipartWithFiles;
import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN;
import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN_DISABLED;
import static java.lang.String.join;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.RandomStringUtils.secure;

import java.io.File;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
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
import lombok.SneakyThrows;

public class JavaHttpIssueApi<T extends Issue> extends AbstractJavaHttpClient implements IssueApi<T> {

    protected final Class<T> issueType;

    /**
     * Creates a new {@link IssueApi} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     * @param issueType      The issue class type
     */
    public JavaHttpIssueApi(HttpClient client, String baseUrl, Authentication authentication, Class<T> issueType) {
        super(client, baseUrl, authentication);
        this.issueType = issueType;
    }

    @Override
    public CompletableFuture<Identity> addIssue(T issue) {
        return sendAsync(requestBuilder(URL_ISSUE_CREATION).POST(body(issue)), Identity.class);
    }

    @Override
    public CompletableFuture<T> getIssue(String issueKey, Set<IssueExpand> expandFlags, Set<String> fields) {
        return sendAsync(requestBuilder(URL_ISSUE_SELECTION, issueKey, IssueExpand.asParameter(expandFlags), join(",", fields)).GET(), issueType);
    }

    @Override
    public CompletableFuture<Void> updateIssue(T issue) {
        return sendAsync(requestBuilder(URL_ISSUE_ACTION, issue.getKey()).PUT(body(issue)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssue(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_ACTION, issueKey).DELETE(), Void.class);
    }

    @Override
    public CompletableFuture<Void> assignIssue(String issueKey, User user) {
        return sendAsync(requestBuilder(URL_ISSUE_ASSIGNEE, issueKey).PUT(body(user)), Void.class);
    }

    @Override
    public CompletableFuture<Transitions> getTransitions(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_TRANSITIONS, issueKey).GET(), Transitions.class);
    }

    @Override
    public CompletableFuture<Void> doTransition(String issueKey, IssueTransition<?> transition) {
        return sendAsync(requestBuilder(URL_ISSUE_TRANSITIONS, issueKey).POST(body(transition)), Void.class);
    }

    @Override
    public CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults, Set<CommentExpand> expandFlags) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENTS_SELECTION, issueKey, startAt, maxResults, CommentExpand.asParameter(expandFlags)).GET(), Comments.class);
    }

    @Override
    public CompletableFuture<Comment> getComment(String issueKey, String id, Set<CommentExpand> expandFlags) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_SELECTION, issueKey, id, CommentExpand.asParameter(expandFlags)).GET(), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> addComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_CREATION, issueKey).POST(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> updateComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_ACTION, issueKey, comment.getId()).PUT(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Void> deleteComment(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT_ACTION, issueKey, id).DELETE(), Void.class);
    }

    @Override
    public CompletableFuture<Void> addVote(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_VOTES, issueKey).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Votes> getVotes(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_VOTES, issueKey).GET(), Votes.class);
    }

    @Override
    public CompletableFuture<Watchers> getWatchers(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_WATCHERS, issueKey).GET(), Watchers.class);
    }

    @Override
    public CompletableFuture<Void> addWatcher(String issueKey, String username) {
        return sendAsync(requestBuilder(URL_ISSUE_WATCHERS, issueKey).POST(body(username)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteWatcher(String issueKey, String username) {
        return sendAsync(requestBuilder(URL_ISSUE_WATCHER, issueKey, username).DELETE(), Void.class);
    }

    @Override
    public CompletableFuture<WorkLog> addWorkLog(String issueKey, WorkLog workLog) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOGS, issueKey).POST(body(workLog)), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLogs> getWorkLogs(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOGS, issueKey).GET(), WorkLogs.class);
    }

    @Override
    public CompletableFuture<WorkLog> getWorkLog(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOG, issueKey, id).GET(), WorkLog.class);
    }

    @Override
    public CompletableFuture<WorkLog> updateWorkLog(String issueKey, WorkLog workLog) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOG, issueKey, workLog.getId()).PUT(body(workLog)), WorkLog.class);
    }

    @Override
    public CompletableFuture<Void> deleteWorkLog(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOG, issueKey, id).DELETE(), Void.class);
    }

    @Override
    public CompletableFuture<Attachment> getAttachment(String id) {
        return sendAsync(requestBuilder(URL_ATTACHMENT, id).GET(), Attachment.class);
    }

    @Override
    public CompletableFuture<InputStream> getAttachmentContent(String url) {
        return sendAsync(requestBuilder(url).GET());
    }

    @Override
    @SneakyThrows
    public CompletableFuture<Attachments> addAttachment(String issueKey, File... files) {
        String boundary = secure().nextAlphanumeric(16);
        HttpRequest.Builder request = requestBuilder(URL_ISSUE_ATTACHMENTS, issueKey)
                .setHeader(HEADER_CONTENT_TYPE, "multipart/form-data; boundary=" + boundary)
                .setHeader(HEADER_ATLASSIAN_TOKEN, HEADER_ATLASSIAN_TOKEN_DISABLED)
                .POST(mimeMultipartData(multipartWithFiles(files), boundary, UTF_8));
        return sendAsync(request, Attachments.class);
    }

    @Override
    public CompletableFuture<Void> deleteAttachment(String id) {
        return sendAsync(requestBuilder(URL_ATTACHMENT, id).DELETE(), Void.class);
    }

    @Override
    public CompletableFuture<RemoteLinks> getRemoteLinks(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINKS, issueKey).GET(), RemoteLinks.class);
    }

    @Override
    public CompletableFuture<RemoteLink> getRemoteLink(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, id).GET(), RemoteLink.class);
    }

    @Override
    public CompletableFuture<Identity> addRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINKS, issueKey).POST(body(remoteLink)), Identity.class);
    }

    @Override
    public CompletableFuture<Void> updateRemoteLink(String issueKey, RemoteLink remoteLink) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, remoteLink.getId()).PUT(body(remoteLink)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteRemoteLink(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, id).DELETE(), Void.class);
    }

    @Override
    public CompletableFuture<Link> getIssueLink(String id) {
        return sendAsync(requestBuilder(URL_ISSUE_LINK, id).GET(), Link.class);
    }

    @Override
    public CompletableFuture<Void> addIssueLink(Link link) {
        return sendAsync(requestBuilder(URL_ISSUE_LINKS).POST(body(link)), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteIssueLink(String id) {
        return sendAsync(requestBuilder(URL_ISSUE_LINK, id).DELETE(), Void.class);
    }

}
