package com.chavaillaz.jira.exception;

public class JiraClientException extends RuntimeException {

    /**
     * Creates a new generic Jira client exception.
     */
    public JiraClientException() {
        super();
    }

    /**
     * Creates a new generic Jira client exception.
     *
     * @param message The error message
     */
    public JiraClientException(String message) {
        super(message);
    }

    /**
     * Creates a new generic Jira client exception.
     *
     * @param message The error message
     * @param cause   The root exception
     */
    public JiraClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new generic Jira client exception.
     *
     * @param cause The root exception
     */
    public JiraClientException(Throwable cause) {
        super(cause);
    }

}
