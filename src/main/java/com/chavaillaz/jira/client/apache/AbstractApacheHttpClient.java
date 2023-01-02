package com.chavaillaz.jira.client.apache;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.hc.core5.http.ContentType.MULTIPART_FORM_DATA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.AbstractHttpClient;
import com.fasterxml.jackson.databind.JavaType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.http.HttpEntity;

@Slf4j
public class AbstractApacheHttpClient extends AbstractHttpClient {

    protected final CloseableHttpAsyncClient client;

    /**
     * Creates a new abstract client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
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
     * @param builder The request builder
     * @return A {@link CompletableFuture} without content
     */
    protected CompletableFuture<Void> sendAsyncReturnVoid(SimpleRequestBuilder builder) {
        return sendAsyncReturnDomain(builder, Void.class);
    }

    /**
     * Sends a request and returns a domain object.
     *
     * @param builder The request builder
     * @param type    The domain object type class
     * @param <T>     The domain object type
     * @return A {@link CompletableFuture} with the deserialized domain object
     */
    protected <T> CompletableFuture<T> sendAsyncReturnDomain(SimpleRequestBuilder builder, Class<T> type) {
        return sendAsyncReturnDomain(builder, objectMapper.constructType(type));
    }

    /**
     * Sends a request and returns a domain object.
     *
     * @param builder The request builder
     * @param type    The domain object type class
     * @param <T>     The domain object type
     * @return A {@link CompletableFuture} with the deserialized domain object
     */
    protected <T> CompletableFuture<T> sendAsyncReturnDomain(SimpleRequestBuilder builder, JavaType type) {
        SimpleHttpRequest request = builder.build();
        CompletableFuture<SimpleHttpResponse> completableFuture = new CompletableFuture<>();
        client.execute(request, new CompletableFutureCallback(this, request, completableFuture));
        return completableFuture.thenApply(SimpleHttpResponse::getBodyText)
                .thenApply(body -> deserialize(body, type));
    }

    /**
     * Sends a request and returns an input stream.
     *
     * @param builder The request builder
     * @return A {@link CompletableFuture} with the input stream
     */
    protected CompletableFuture<InputStream> sendAsyncReturnStream(SimpleRequestBuilder builder) {
        SimpleHttpRequest request = builder.build();
        CompletableFuture<SimpleHttpResponse> completableFuture = new CompletableFuture<>();
        client.execute(request, new CompletableFutureCallback(this, request, completableFuture));
        return completableFuture.thenApply(SimpleHttpResponse::getBodyBytes)
                .thenApply(ByteArrayInputStream::new);
    }

    /**
     * Sends a multipart request and returns a domain object.
     *
     * @param requestBuilder   The request builder
     * @param multipartBuilder The multipart builder
     * @param type             The domain object type class
     * @param <T>              The domain object type
     * @return A {@link CompletableFuture} with the deserialized domain object
     */
    @SneakyThrows
    protected <T> CompletableFuture<T> sendAsyncReturnDomain(SimpleRequestBuilder requestBuilder, MultipartEntityBuilder multipartBuilder, Class<T> type) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String boundary = randomAlphanumeric(16);
        multipartBuilder.setBoundary(boundary);
        try (HttpEntity entity = multipartBuilder.build()) {
            entity.writeTo(outputStream);
        }
        requestBuilder.setHeader(HEADER_CONTENT_TYPE, MULTIPART_FORM_DATA.getMimeType() + "; boundary=" + boundary)
                .setHeader(HEADER_ATLASSIAN_TOKEN, HEADER_ATLASSIAN_TOKEN_DISABLED)
                .setBody(outputStream.toByteArray(), MULTIPART_FORM_DATA);
        return sendAsyncReturnDomain(requestBuilder, type);
    }

    public void close() throws Exception {
        client.close();
    }

}
