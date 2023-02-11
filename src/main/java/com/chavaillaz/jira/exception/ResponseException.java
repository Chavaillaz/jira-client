package com.chavaillaz.jira.exception;

import static java.lang.String.join;

import java.util.List;

import lombok.Getter;

@Getter
public class ResponseException extends JiraClientException {

    private final Integer statusCode;
    private final List<String> errors;

    /**
     * Creates a new Jira response exception.
     * This means the request didn't return success code.
     *
     * @param statusCode The status code
     * @param errors     The errors returned
     */
    public ResponseException(int statusCode, List<String> errors) {
        super("Jira responded with status " + statusCode + " with errors: " + join(", ", errors));
        this.statusCode = statusCode;
        this.errors = errors;
    }

}
