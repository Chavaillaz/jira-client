package com.chavaillaz.client.jira.java;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.security.Authentication;
import com.chavaillaz.client.jira.JiraConstants.ApiVersion;
import com.chavaillaz.client.jira.api.VersionApi;
import com.chavaillaz.client.jira.domain.Version;

public class JavaHttpVersionApi extends AbstractJavaHttpClient implements VersionApi {

    /**
     * Creates a new {@link VersionApi} using Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jira (including API version path)
     * @param authentication The authentication information
     * @param apiVersion     The API version to use
     */
    public JavaHttpVersionApi(HttpClient client, String baseUrl, Authentication authentication, ApiVersion apiVersion) {
        super(client, baseUrl, authentication, apiVersion);
    }

    // ...existing code...
    public CompletableFuture<Version> addVersion(Version version) {
        return sendAsync(requestBuilder(URL_VERSION, EMPTY).POST(body(version)), Version.class);
    }

    @Override
    public CompletableFuture<Version> getVersion(String versionId) {
        return sendAsync(requestBuilder(URL_VERSION, versionId).GET(), Version.class);
    }

    @Override
    public CompletableFuture<Version> updateVersion(Version version) {
        return sendAsync(requestBuilder(URL_VERSION, version.getId()).PUT(body(version)), Version.class);
    }

    @Override
    public CompletableFuture<Void> deleteVersion(String versionId) {
        return sendAsync(requestBuilder(URL_VERSION, versionId).DELETE(), Void.class);
    }

}
