package com.chavaillaz.client.jira.vertx;

import static com.chavaillaz.client.jira.JiraConstants.jiraObjectMapper;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.exception.JiraResponseException;
import io.vertx.ext.web.client.WebClient;

public abstract class AbstractVertxHttpClient extends com.chavaillaz.client.common.vertx.AbstractVertxHttpClient {

    /**
     * Creates a new abstract client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    protected AbstractVertxHttpClient(WebClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
        this.objectMapper = jiraObjectMapper();
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JiraResponseException(code, body);
    }

}
