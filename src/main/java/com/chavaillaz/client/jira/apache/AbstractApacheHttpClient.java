package com.chavaillaz.client.jira.apache;

import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN;
import static com.chavaillaz.client.jira.JiraConstants.HEADER_ATLASSIAN_TOKEN_DISABLED;
import static com.chavaillaz.client.jira.JiraConstants.jiraObjectMapper;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.exception.JiraResponseException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

@Slf4j
public class AbstractApacheHttpClient extends com.chavaillaz.client.common.apache.AbstractApacheHttpClient {

    /**
     * Creates a new abstract client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public AbstractApacheHttpClient(CloseableHttpAsyncClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
        this.objectMapper = jiraObjectMapper();
    }

    @SneakyThrows
    protected <T> CompletableFuture<T> sendAsync(SimpleRequestBuilder requestBuilder, MultipartEntityBuilder multipartBuilder, Class<T> returnType) {
        return super.sendAsync(requestBuilder.setHeader(HEADER_ATLASSIAN_TOKEN, HEADER_ATLASSIAN_TOKEN_DISABLED), multipartBuilder, returnType);
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JiraResponseException(code, body);
    }

}
