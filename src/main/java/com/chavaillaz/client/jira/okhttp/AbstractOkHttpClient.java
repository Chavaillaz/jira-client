package com.chavaillaz.client.jira.okhttp;

import static com.chavaillaz.client.jira.JiraConstants.jiraObjectMapper;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.exception.JiraResponseException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
public class AbstractOkHttpClient extends com.chavaillaz.client.common.okhttp.AbstractOkHttpClient {

    /**
     * Creates a new abstract client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jira
     * @param authentication The authentication information
     */
    public AbstractOkHttpClient(OkHttpClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
        this.objectMapper = jiraObjectMapper();
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JiraResponseException(code, body);
    }

}
