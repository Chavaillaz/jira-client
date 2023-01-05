package com.chavaillaz.jira.client.okhttp;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.AbstractHttpClient;
import com.chavaillaz.jira.domain.ErrorMessages;
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
            List<String> errors;
            try (ResponseBody body = response.body()) {
                errors = parseErrors(body);
            }
            future.completeExceptionally(new ResponseException(response.code(), errors));
        } else {
            future.complete(response);
        }
    }

    protected List<String> parseErrors(ResponseBody body) {
        if (body != null) {
            String bodyString = null;
            try {
                // Can only be consumed once
                bodyString = body.string();
                if (isNotBlank(bodyString)) {
                    return client.deserialize(bodyString, ErrorMessages.class);
                }
            } catch (Exception e) {
                if (isNotBlank(bodyString)) {
                    return List.of(bodyString);
                }
            }
        }
        return emptyList();
    }

    @Override
    public void onFailure(Call call, IOException exception) {
        log.debug("{} failed: {}", call.request(), exception.getMessage());
        future.completeExceptionally(exception);
    }


}
