package com.chavaillaz.jira.client;

import com.chavaillaz.jira.domain.Issue;
import com.chavaillaz.jira.utils.ProxyConfiguration;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.List;

/**
 * Abstract class implementing common parts of the {@link JiraClient}.
 *
 * @param <C> HTTP Client to use
 */
@RequiredArgsConstructor
public abstract class AbstractJiraClient<C, I extends Issue, L extends List<? extends I>> implements JiraClient<I, L> {

    public static final String BASE_API = "/rest/api/2/";

    protected final String jiraUrl;
    protected String authentication;
    protected ProxyConfiguration proxy;

    /**
     * Creates a new HTTP client that will be used to communicate with Jira REST endpoints.
     *
     * @return The HTTP client
     */
    protected abstract C newHttpClient();

    @Override
    public JiraClient<I, L> withProxy(String host, Integer port) {
        this.proxy = ProxyConfiguration.from(host, port);
        return this;
    }

    @Override
    public JiraClient<I, L> withProxy(String url) {
        this.proxy = ProxyConfiguration.from(url);
        return this;
    }

    @Override
    public JiraClient<I, L> withAuthentication(String token) {
        this.authentication = "Bearer " + token;
        return this;
    }

    @Override
    public JiraClient<I, L> withAuthentication(String username, String password) {
        this.authentication = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        return this;
    }

}
