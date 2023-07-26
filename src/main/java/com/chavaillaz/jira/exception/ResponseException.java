package com.chavaillaz.jira.exception;

import static com.chavaillaz.jira.client.JiraConstants.extractHtmlErrors;
import static com.chavaillaz.jira.client.JiraConstants.extractJsonErrors;
import static java.lang.String.join;
import static java.util.Optional.ofNullable;

import lombok.Getter;

@Getter
public class ResponseException extends JiraClientException {

    private final Integer statusCode;
    private final String body;

    /**
     * Creates a new Jira response exception, meaning the request didn't return a success code.
     * The exception message is computed based on the given body, trying to parse error messages in JSON or HTML.
     *
     * @param statusCode The status code
     * @param body       The content body
     */
    public ResponseException(int statusCode, String body) {
        super(errorMessage(statusCode, body));
        this.statusCode = statusCode;
        this.body = body;
    }

    private static String errorMessage(Integer statusCode, String content) {
        return "Jira responded with " + statusCode + ": " +
                ofNullable(extractJsonErrors(content))
                        .map(errors -> join(". ", errors))
                        .or(() -> ofNullable(extractHtmlErrors(content)))
                        .orElse(content);
    }

}
