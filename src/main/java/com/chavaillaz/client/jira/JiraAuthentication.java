package com.chavaillaz.client.jira;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Base64;

import com.chavaillaz.client.Authentication;
import lombok.NoArgsConstructor;

/**
 * Implementation of the authentication specificities for Jira.
 */
@NoArgsConstructor
public class JiraAuthentication extends Authentication {

    /**
     * Creates a new authentication configuration.
     *
     * @param type     The type of authentication
     * @param username The username or {@code null} for anonymous access
     * @param password The password, token or {@code null} for anonymous access
     */
    public JiraAuthentication(AuthenticationType type, String username, String password) {
        super(type, username, password);
    }

    /**
     * Encodes a {@link String} to base 64 format.
     *
     * @param value The value to encode
     * @return The encoded value
     */
    private static String encodeBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    /**
     * Gets the HTTP Header {@code Authorization} to transmit for authentication.
     *
     * @return The authorization header
     */
    public String getAuthorizationHeader() {
        return switch (getType()) {
            case ANONYMOUS -> EMPTY;
            case PASSWORD -> "Basic " + encodeBase64(getUsername() + ":" + getPassword());
            case TOKEN -> "Bearer " + encodeBase64(getPassword());
        };
    }

}
