package com.chavaillaz.jira.exception;

public class DeserializationException extends JiraClientException {

    /**
     * Creates a new deserialization exception.
     *
     * @param content   The content to deserialize
     * @param type      The type of the content to deserialize
     * @param exception The exception thrown by Jackson
     */
    public DeserializationException(String content, Class<?> type, Throwable exception) {
        super("Unable to deserialize type " + type.getSimpleName() + " from " + content, exception);
    }

}
