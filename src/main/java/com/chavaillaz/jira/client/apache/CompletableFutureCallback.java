package com.chavaillaz.jira.client.apache;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jira.client.AbstractHttpClient;
import com.chavaillaz.jira.domain.ErrorMessages;
import com.chavaillaz.jira.exception.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

/**
 * Future callback for Apache HTTP Client in order to transfer state to a {@link CompletableFuture}.
 * It completes exceptionally if the response code is not in the 2xx range (see {@link ResponseException})
 * or if a connection failure happens (e.g. timeout).
 */
@Slf4j
@AllArgsConstructor
public class CompletableFutureCallback implements FutureCallback<SimpleHttpResponse> {

    private final AbstractHttpClient client;
    private final SimpleHttpRequest request;
    private final CompletableFuture<SimpleHttpResponse> future;

    @Override
    public void completed(SimpleHttpResponse response) {
        log.debug("Request {} completed: {}", request, response);
        if (response.getCode() >= 300) {
            List<String> errors = new ArrayList<>();
            String body = response.getBodyText();
            if (isNotBlank(body)) {
                errors = client.deserialize(body, ErrorMessages.class);
            }
            future.completeExceptionally(new ResponseException(response.getCode(), errors));
        } else {
            future.complete(response);
        }
    }

    @Override
    public void failed(Exception exception) {
        log.debug("Request {} failed: {}", request, exception.getMessage());
        future.completeExceptionally(exception);
    }

    @Override
    public void cancelled() {
        log.debug("Request {} cancelled", request);
        future.cancel(false);
    }

}
