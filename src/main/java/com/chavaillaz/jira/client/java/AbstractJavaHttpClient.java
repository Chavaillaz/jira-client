package com.chavaillaz.jira.client.java;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.AbstractHttpClient;
import com.chavaillaz.jira.domain.ErrorMessages;
import com.chavaillaz.jira.exception.ResponseException;
import com.fasterxml.jackson.databind.JavaType;

public class AbstractJavaHttpClient extends AbstractHttpClient {

    protected final HttpClient client;

    /**
     * Creates a new abstract client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     */
    public AbstractJavaHttpClient(HttpClient client, String baseUrl, String authentication) {
        super(baseUrl, authentication);
        this.client = client;
    }

    /**
     * Creates a new request builder based on the given URL and replaces the parameters in it by the given ones.
     *
     * @param url        The URL with possible parameters in it (using braces)
     * @param parameters The parameters value to replace in the URL (in the right order)
     * @return The request builder having the URL and authorization header set
     */
    protected HttpRequest.Builder requestBuilder(String url, Object... parameters) {
        return HttpRequest.newBuilder()
                .uri(url(url, parameters))
                .header(HEADER_AUTHORIZATION, getAuthentication())
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_JSON);
    }

    /**
     * Creates the body publisher that is serializing the given object.
     *
     * @param object The object to serialize
     * @return The corresponding body publisher
     */
    protected BodyPublisher body(Object object) {
        return BodyPublishers.ofString(serialize(object));
    }

    /**
     * Checks the response and throws an exception in case of unsuccessful call (not 2xx status code).
     *
     * @param response The HTTP response received
     * @param <T>      The response body type
     * @return The same HTTP response as in parameter
     */
    protected <T> HttpResponse<T> checkResponse(HttpResponse<T> response) {
        if (response.statusCode() >= 300) {
            List<String> errors = new ArrayList<>();
            if (response.body() instanceof String bodyString && isNotBlank(bodyString)) {
                errors = deserialize(bodyString, ErrorMessages.class);
            }
            throw new ResponseException(response.statusCode(), errors);
        }
        return response;
    }

    /**
     * Sends a request and returns a domain object.
     *
     * @param requestBuilder The request builder
     * @param returnType     The content type of the content received
     * @param <T>            The content type
     * @return A {@link CompletableFuture} with the deserialized response body
     */
    protected <T> CompletableFuture<T> sendAsync(HttpRequest.Builder requestBuilder, Class<T> returnType) {
        return sendAsync(requestBuilder, objectMapper.constructType(returnType));
    }

    /**
     * Sends a request and returns a domain object.
     *
     * @param requestBuilder The request builder
     * @param returnType     The content type of the content received
     * @param <T>            The content type
     * @return A {@link CompletableFuture} with the deserialized response body
     */
    protected <T> CompletableFuture<T> sendAsync(HttpRequest.Builder requestBuilder, JavaType returnType) {
        return client.sendAsync(requestBuilder.build(), BodyHandlers.ofString())
                .thenApply(this::checkResponse)
                .thenApply(response -> deserialize(response.body(), returnType));
    }

    /**
     * Sends a request and returns an input stream.
     *
     * @param requestBuilder The request builder
     * @return A {@link CompletableFuture} with the input stream
     */
    protected CompletableFuture<InputStream> sendAsync(HttpRequest.Builder requestBuilder) {
        return client.sendAsync(requestBuilder.build(), BodyHandlers.ofInputStream())
                .thenApply(this::checkResponse)
                .thenApply(HttpResponse::body);
    }

    public void close() {
        // Java client does not need to be closed
    }

}
