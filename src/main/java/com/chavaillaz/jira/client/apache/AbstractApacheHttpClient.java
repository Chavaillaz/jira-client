package com.chavaillaz.jira.client.apache;

import com.chavaillaz.jira.client.AbstractHttpClient;
import com.fasterxml.jackson.databind.JavaType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.http.HttpEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.hc.core5.http.ContentType.MULTIPART_FORM_DATA;

@Slf4j
public class AbstractApacheHttpClient extends AbstractHttpClient {

    protected final CloseableHttpAsyncClient client;

    /**
     * Creates a new abstract client based on Apache HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     */
    public AbstractApacheHttpClient(CloseableHttpAsyncClient client, String baseUrl, String authentication) {
        super(baseUrl, authentication);
        this.client = client;
        this.client.start();
    }

    /**
     * Enriches a request builder based on the given URL and replaces the parameters in it by the given ones.
     *
     * @param builder    The request builder to use
     * @param url        The URL with possible parameters in it (using braces)
     * @param parameters The parameters value to replace in the URL (in the right order)
     * @return The request builder having the URL and authorization header set
     */
    protected SimpleRequestBuilder requestBuilder(SimpleRequestBuilder builder, String url, Object... parameters) {
        return builder.setUri(url(url, parameters))
                .setHeader(HEADER_AUTHORIZATION, getAuthentication())
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_JSON);
    }

    /**
     * Sends a request and discards the received content.
     *
     * @param request The request builder
     * @return A {@link CompletableFuture} without content
     */
    protected CompletableFuture<Void> sendAsyncReturnVoid(SimpleRequestBuilder request) {
        return sendAsyncReturnDomain(request, Void.class);
    }

    /**
     * Sends a request and returns a domain object.
     *
     * @param request The request builder
     * @param type    The domain object type class
     * @param <T>     The domain object type
     * @return A {@link CompletableFuture} with the deserialized domain object
     */
    protected <T> CompletableFuture<T> sendAsyncReturnDomain(SimpleRequestBuilder request, Class<T> type) {
        return sendAsyncReturnDomain(request, objectMapper.constructType(type));
    }

    /**
     * Sends a request and returns a domain object.
     *
     * @param request The request builder
     * @param type    The domain object type class
     * @param <T>     The domain object type
     * @return A {@link CompletableFuture} with the deserialized domain object
     */
    protected <T> CompletableFuture<T> sendAsyncReturnDomain(SimpleRequestBuilder request, JavaType type) {
        CompletableFuture<SimpleHttpResponse> completableFuture = new CompletableFuture<>();
        client.execute(request.build(), new CompletableFutureCallback(this, completableFuture));
        return completableFuture.thenApply(SimpleHttpResponse::getBodyText)
                .thenApply(body -> deserialize(body, type));
    }

    /**
     * Sends a request and returns an input stream.
     *
     * @param request The request builder
     * @return A {@link CompletableFuture} with the input stream
     */
    protected CompletableFuture<InputStream> sendAsyncReturnStream(SimpleRequestBuilder request) {
        CompletableFuture<SimpleHttpResponse> completableFuture = new CompletableFuture<>();
        client.execute(request.build(), new CompletableFutureCallback(this, completableFuture));
        return completableFuture.thenApply(SimpleHttpResponse::getBodyBytes)
                .thenApply(ByteArrayInputStream::new);
    }

    /**
     * Sends a multipart request and returns a domain object.
     *
     * @param request   The request builder
     * @param multipart The multipart builder
     * @param type      The domain object type class
     * @param <T>       The domain object type
     * @return A {@link CompletableFuture} with the deserialized domain object
     */
    @SneakyThrows
    protected <T> CompletableFuture<T> sendAsyncMultipartReturnDomain(SimpleRequestBuilder request, MultipartEntityBuilder multipart, Class<T> type) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String boundary = randomAlphanumeric(16);
        multipart.setBoundary(boundary);
        try (HttpEntity entity = multipart.build()) {
            entity.writeTo(outputStream);
        }
        request.setHeader(HEADER_CONTENT_TYPE, MULTIPART_FORM_DATA.getMimeType() + "; boundary=" + boundary)
                .setHeader(HEADER_ATLASSIAN_TOKEN, HEADER_ATLASSIAN_TOKEN_DISABLED)
                .setBody(outputStream.toByteArray(), MULTIPART_FORM_DATA);
        CompletableFuture<SimpleHttpResponse> completableFuture = new CompletableFuture<>();
        client.execute(request.build(), new CompletableFutureCallback(this, completableFuture));
        return completableFuture.thenApply(SimpleHttpResponse::getBodyText)
                .thenApply(body -> deserialize(body, type));
    }

    public void close() throws Exception {
        client.close();
    }

}
