package com.chavaillaz.jira.client.okhttp;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.AbstractHttpClient;
import com.chavaillaz.jira.exception.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Callback for OkHttp Client in order to transfer state to a {@link CompletableFuture}.
 * It completes exceptionally if the response code is not in the 2xx range (see {@link ResponseException})
 * or if a connection failure happens (e.g. timeout).
 */
@Slf4j
@AllArgsConstructor
public class CompletableFutureCallback implements Callback {

    private final AbstractHttpClient client;
    private final CompletableFuture<Response> future;

    @Override
    public void onResponse(Call call, Response response) {
        log.debug("{} completed: {}", call.request(), response);

        if (response.code() >= 300) {
            String content;
            try (ResponseBody body = response.body()) {
                content = body != null ? body.string() : null;
            } catch (Exception e) {
                content = e.getMessage();
            }
            future.completeExceptionally(new ResponseException(response.code(), content));
        } else {
            future.complete(response);
        }
    }

    @Override
    public void onFailure(Call call, IOException exception) {
        log.debug("{} failed: {}", call.request(), exception.getMessage());
        future.completeExceptionally(exception);
    }


}
