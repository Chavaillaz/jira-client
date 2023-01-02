package com.chavaillaz.jira.client.okhttp;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.AbstractHttpClient;
import com.fasterxml.jackson.databind.JavaType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Slf4j
public class AbstractOkHttpClient extends AbstractHttpClient {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse(HEADER_CONTENT_JSON);

    protected final OkHttpClient client;

    /**
     * Creates a new abstract client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication header (nullable)
     */
    public AbstractOkHttpClient(OkHttpClient client, String baseUrl, String authentication) {
        super(baseUrl, authentication);
        this.client = client;
    }

    /**
     * Creates a request builder based on the given URL and replaces the parameters in it by the given ones.
     *
     * @param url        The URL with possible parameters in it (using braces)
     * @param parameters The parameters value to replace in the URL (in the right order)
     * @return The request builder having the URL and authorization header set
     */
    protected Request.Builder requestBuilder(String url, Object... parameters) {
        return new Request.Builder()
                .url(url(url, parameters).toString())
                .header(HEADER_AUTHORIZATION, getAuthentication())
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_JSON);
    }

    /**
     * Creates a request body with the given object serialized as JSON.
     *
     * @param object The object to serialize
     * @return The corresponding request body
     */
    protected RequestBody body(Object object) {
        return RequestBody.create(serialize(object), MEDIA_TYPE_JSON);
    }

    /**
     * Creates an empty request body.
     *
     * @return The corresponding request body
     */
    protected RequestBody body() {
        return RequestBody.create(EMPTY, null);
    }

    /**
     * Sends a request and discards the received content.
     *
     * @param builder The request builder
     * @return A {@link CompletableFuture} without content
     */
    protected CompletableFuture<Void> sendAsyncReturnVoid(Request.Builder builder) {
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
    protected <T> CompletableFuture<T> sendAsyncReturnDomain(Request.Builder builder, Class<T> type) {
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
    protected <T> CompletableFuture<T> sendAsyncReturnDomain(Request.Builder builder, JavaType type) {
        CompletableFuture<Response> completableFuture = new CompletableFuture<>();
        client.newCall(builder.build()).enqueue(new CompletableFutureCallback(this, completableFuture));
        return completableFuture.thenApply(response -> handleResponse(response, type));
    }

    /**
     * Sends a request and returns an input stream.
     *
     * @param builder The request builder
     * @return A {@link CompletableFuture} with the input stream
     */
    protected CompletableFuture<InputStream> sendAsyncReturnStream(Request.Builder builder) {
        CompletableFuture<Response> completableFuture = new CompletableFuture<>();
        client.newCall(builder.build()).enqueue(new CompletableFutureCallback(this, completableFuture));
        return completableFuture.thenApply(Response::body)
                .thenApply(ResponseBody::byteStream);
    }

    @SneakyThrows
    protected <T> T handleResponse(Response response, JavaType type) {
        try (ResponseBody body = response.body()) {
            return body != null ? deserialize(body.string(), type) : null;
        }
    }

    public void close() throws Exception {
        // OkHttp client does not need to be closed
    }

}
