package com.chavaillaz.client.jira.java;

import static com.chavaillaz.client.jira.JiraConstants.jiraObjectMapper;

import java.net.http.HttpClient;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.exception.JiraResponseException;

public class AbstractJavaHttpClient extends com.chavaillaz.client.common.java.AbstractJavaHttpClient {

    /**
     * Creates a new abstract client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public AbstractJavaHttpClient(HttpClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
        this.objectMapper = jiraObjectMapper();
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JiraResponseException(code, body);
    }

}
