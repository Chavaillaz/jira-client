package com.chavaillaz.jira.exception;

public class SerializationException extends JiraClientException {

    /**
     * Creates a new serialization exception.
     *
     * @param object    The content to serialize
     * @param exception The exception thrown by Jackson
     */
    public SerializationException(Object object, Throwable exception) {
        super("Unable to serialize type " + object.getClass().getSimpleName() + " from " + object, exception);
    }

}
