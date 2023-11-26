package com.chavaillaz.client.jira.java;

import static com.chavaillaz.client.jira.JiraConstants.jiraObjectMapper;

import java.net.http.HttpClient;

import com.chavaillaz.client.exception.ResponseException;
import com.chavaillaz.client.jira.JiraAuthentication;
import com.chavaillaz.client.jira.exception.JiraResponseException;

public class AbstractJavaHttpClient extends com.chavaillaz.client.java.AbstractJavaHttpClient<JiraAuthentication> {

    /**
     * Creates a new abstract client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public AbstractJavaHttpClient(HttpClient client, String baseUrl, JiraAuthentication authentication) {
        super(client, baseUrl, authentication);
        this.objectMapper = jiraObjectMapper();
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JiraResponseException(code, body);
    }

}
