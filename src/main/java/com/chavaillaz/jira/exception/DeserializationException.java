package com.chavaillaz.jira.exception;

import com.fasterxml.jackson.databind.JavaType;

import java.text.MessageFormat;

public class DeserializationException extends JiraClientException {

    /**
     * Creates a new deserialization exception.
     *
     * @param content   The content to deserialize
     * @param type      The type of the content to deserialize
     * @param exception The exception thrown by Jackson
     */
    public DeserializationException(String content, Class<?> type, Throwable exception) {
        super(errorMessage(content, type.getSimpleName()), exception);
    }

    /**
     * Creates a new deserialization exception.
     *
     * @param content   The content to deserialize
     * @param type      The type of the content to deserialize
     * @param exception The exception thrown by Jackson
     */
    public DeserializationException(String content, JavaType type, Throwable exception) {
        super(errorMessage(content, type.getTypeName()), exception);
    }

    private static String errorMessage(String content, String type) {
        return MessageFormat.format("Unable to deserialize type {0} from {1}", type, content);
    }

}
