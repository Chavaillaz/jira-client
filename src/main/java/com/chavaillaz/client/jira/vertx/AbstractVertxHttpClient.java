package com.chavaillaz.client.jira.vertx;

import static com.chavaillaz.client.jira.JiraConstants.jiraObjectMapper;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.JiraConstants.ApiVersion;
import com.chavaillaz.client.jira.exception.JiraResponseException;
import io.vertx.ext.web.client.WebClient;

/**
 * Abstract Vert.x HTTP client for JIRA API operations.
 * Supports both JIRA REST API v2 and v3.
 */
public abstract class AbstractVertxHttpClient extends com.chavaillaz.client.common.vertx.AbstractVertxHttpClient {

    /**
     * The JIRA REST API version being used.
     */
    protected final ApiVersion apiVersion;

    /**
     * Creates a new abstract client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jira (including API version path)
     * @param authentication The authentication information
     * @param apiVersion     The API version to use
     */
    protected AbstractVertxHttpClient(WebClient client, String baseUrl, Authentication authentication, ApiVersion apiVersion) {
        super(client, baseUrl, authentication);
        this.objectMapper = jiraObjectMapper();
        this.apiVersion = apiVersion;
    }

    /**
     * Builds a URL by appending the URL pattern to the base URL.
     * This method can be used for API version-specific URL construction if needed in the future.
     *
     * @param urlPattern The URL pattern to append
     * @return The complete URL
     */
    protected String buildUrl(String urlPattern) {
        return baseUrl + urlPattern;
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JiraResponseException(code, body);
    }

    @Override
    public ResponseException responseException(String method, String url, int code, String body) {
        return new JiraResponseException(method, url, code, body);
    }

}
