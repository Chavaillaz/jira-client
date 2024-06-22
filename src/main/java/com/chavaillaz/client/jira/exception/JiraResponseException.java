package com.chavaillaz.client.jira.exception;

import static java.lang.String.join;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.SPACE;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.jira.JiraConstants;

public class JiraResponseException extends ResponseException {

    /**
     * Creates a new Jira response exception, meaning the request didn't return a success code.
     * The exception message is computed based on the given body, trying to parse error messages in JSON or HTML.
     *
     * @param code The status code
     * @param body The content body
     */
    public JiraResponseException(int code, String body) {
        super(code, body, errorMessage(code, body));
    }

    /**
     * Creates a new Jira response exception, meaning the request didn't return a success code.
     * The exception message is computed based on the given body, trying to parse error messages in JSON or HTML.
     *
     * @param method The request HTTP method
     * @param url    The request URL
     * @param code   The status code
     * @param body   The content body
     */
    public JiraResponseException(String method, String url, int code, String body) {
        super(code, url, errorMessage(code, body) + " for " + method + SPACE + url);
    }

    private static String errorMessage(Integer code, String content) {
        return "Jira responded with " + code + ": " +
                ofNullable(JiraConstants.extractJsonErrors(content))
                        .map(errors -> join(". ", errors))
                        .or(() -> ofNullable(JiraConstants.extractHtmlErrors(content)))
                        .orElse(content);
    }

}
