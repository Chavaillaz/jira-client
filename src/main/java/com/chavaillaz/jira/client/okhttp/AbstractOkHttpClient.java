package com.chavaillaz.jira.client.okhttp;

import static com.chavaillaz.jira.client.JiraConstants.jiraObjectMapper;

import com.chavaillaz.client.exception.ResponseException;
import com.chavaillaz.jira.client.JiraAuthentication;
import com.chavaillaz.jira.exception.JiraResponseException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
public class AbstractOkHttpClient extends com.chavaillaz.client.okhttp.AbstractOkHttpClient<JiraAuthentication> {

    /**
     * Creates a new abstract client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public AbstractOkHttpClient(OkHttpClient client, String baseUrl, JiraAuthentication authentication) {
        super(client, baseUrl, authentication);
        this.objectMapper = jiraObjectMapper();
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JiraResponseException(code, body);
    }

}
