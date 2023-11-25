package com.chavaillaz.jira.client.java;

import static com.chavaillaz.jira.client.JiraConstants.jiraObjectMapper;

import java.net.http.HttpClient;

import com.chavaillaz.client.exception.ResponseException;
import com.chavaillaz.jira.client.JiraAuthentication;
import com.chavaillaz.jira.exception.JiraResponseException;

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
