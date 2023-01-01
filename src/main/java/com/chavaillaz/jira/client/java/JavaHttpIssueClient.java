package com.chavaillaz.jira.client.java;

import static com.chavaillaz.jira.client.java.JavaHttpUtils.multipartWithFiles;
import static com.chavaillaz.jira.client.java.JavaHttpUtils.ofMimeMultipartData;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.io.File;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.IssueClient;
import com.chavaillaz.jira.domain.Attachment;
import com.chavaillaz.jira.domain.Attachments;
import com.chavaillaz.jira.domain.Comment;
import com.chavaillaz.jira.domain.Comments;
import com.chavaillaz.jira.domain.Identity;
import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.domain.IssueTransition;
import com.chavaillaz.jira.domain.Link;
import com.chavaillaz.jira.domain.RemoteLink;
import com.chavaillaz.jira.domain.RemoteLinks;
import com.chavaillaz.jira.domain.Transitions;
import com.chavaillaz.jira.domain.User;
import com.chavaillaz.jira.domain.Votes;
import com.chavaillaz.jira.domain.Watchers;
import com.chavaillaz.jira.domain.WorkLog;
import com.chavaillaz.jira.domain.WorkLogs;
import lombok.SneakyThrows;

public class JavaHttpIssueClient<T extends Issue> extends AbstractJavaHttpClient implements IssueClient<T> {

    protected final Class<T> issueType;

    /**
     * Creates a new {@link IssueClient} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     * @param issueType      The issue class type
     */
    public JavaHttpIssueClient(HttpClient client, String baseUrl, String authentication, Class<T> issueType) {
        super(client, baseUrl, authentication);
        this.issueType = issueType;
    }

    @Override
    public CompletableFuture<Identity> addIssue(T issue) {
        return sendAsync(requestBuilder(URL_ISSUES).POST(body(issue)), Identity.class);
    }

    @Override
    public CompletableFuture<T> getIssue(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_DETAILS, issueKey).GET(), issueType);
    }

    @Override
    public CompletableFuture<Void> updateIssue(T issue) {
        return sendAsync(requestBuilder(URL_ISSUE, issue.getKey()).PUT(body(issue)));
    }

    @Override
    public CompletableFuture<Void> deleteIssue(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE, issueKey).DELETE());
    }

    @Override
    public CompletableFuture<Void> assignIssue(String issueKey, User user) {
        return sendAsync(requestBuilder(URL_ISSUE_ASSIGNEE, issueKey).PUT(body(user)));
    }

    @Override
    public CompletableFuture<Transitions> getTransitions(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_TRANSITIONS, issueKey).GET(), Transitions.class);
    }

    @Override
    public CompletableFuture<Void> doTransition(String issueKey, IssueTransition transition) {
        return sendAsync(requestBuilder(URL_ISSUE_TRANSITIONS, issueKey).POST(body(transition)));
    }

    @Override
    public CompletableFuture<Comments> getComments(String issueKey, Integer startAt, Integer maxResults) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENTS_SELECTION, issueKey, startAt, maxResults).GET(), Comments.class);
    }

    @Override
    public CompletableFuture<Comment> getComment(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT, issueKey, id).GET(), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> addComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENTS, issueKey).POST(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Comment> updateComment(String issueKey, Comment comment) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT, issueKey, comment.getId()).PUT(body(comment)), Comment.class);
    }

    @Override
    public CompletableFuture<Void> deleteComment(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_COMMENT, issueKey, id).DELETE());
    }

    @Override
    public CompletableFuture<Void> addVote(String issueKey) {
        return sendAsync(requestBuilder(URL_ISSUE_VOTES, issueKey).POST(noBody()));
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
        return sendAsync(requestBuilder(URL_ISSUE_WATCHERS, issueKey).POST(body(username)));
    }

    @Override
    public CompletableFuture<Void> deleteWatcher(String issueKey, String username) {
        return sendAsync(requestBuilder(URL_ISSUE_WATCHER, issueKey, username).DELETE());
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
        return sendAsync(requestBuilder(URL_ISSUE_WORK_LOG, issueKey, id).DELETE());
    }

    @Override
    public CompletableFuture<Attachment> getAttachment(String id) {
        return sendAsync(requestBuilder(URL_ATTACHMENT, id).GET(), Attachment.class);
    }

    @Override
    public CompletableFuture<InputStream> getAttachmentContent(String url) {
        return client.sendAsync(requestBuilder(url).GET().build(), BodyHandlers.ofInputStream())
                .thenApply(this::checkResponse)
                .thenApply(HttpResponse::body);
    }

    @Override
    @SneakyThrows
    public CompletableFuture<Attachments> addAttachment(String issueKey, File... files) {
        String boundary = randomAlphanumeric(16);
        HttpRequest.Builder request = requestBuilder(URL_ISSUE_ATTACHMENTS, issueKey)
                .setHeader(HEADER_CONTENT_TYPE, "multipart/form-data; boundary=" + boundary)
                .setHeader(HEADER_ATLASSIAN_TOKEN, HEADER_ATLASSIAN_TOKEN_DISABLED)
                .POST(ofMimeMultipartData(multipartWithFiles(files), boundary, UTF_8));
        return sendAsync(request, Attachments.class);
    }

    @Override
    public CompletableFuture<Void> deleteAttachment(String id) {
        return sendAsync(requestBuilder(URL_ATTACHMENT, id).DELETE());
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
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, remoteLink.getId()).PUT(body(remoteLink)));
    }

    @Override
    public CompletableFuture<Void> deleteRemoteLink(String issueKey, String id) {
        return sendAsync(requestBuilder(URL_ISSUE_REMOTE_LINK, issueKey, id).DELETE());
    }

    @Override
    public CompletableFuture<Link> getIssueLink(String id) {
        return sendAsync(requestBuilder(URL_ISSUE_LINK, id).GET(), Link.class);
    }

    @Override
    public CompletableFuture<Void> addIssueLink(Link link) {
        return sendAsync(requestBuilder(URL_ISSUE_LINKS).POST(body(link)));
    }

    @Override
    public CompletableFuture<Void> deleteIssueLink(String id) {
        return sendAsync(requestBuilder(URL_ISSUE_LINK, id).DELETE());
    }

}
