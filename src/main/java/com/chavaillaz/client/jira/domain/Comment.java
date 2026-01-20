package com.chavaillaz.client.jira.domain;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Comparable<Comment> {

    private User author;

    /**
     * The comment body.
     * In API v2, this is a String.
     * In API v3, this is an Atlassian Document Format (ADF) object.
     */
    @JsonProperty("body")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Object bodyInternal;

    private String renderedBody;

    private OffsetDateTime created;

    private String id;

    private String self;

    private User updateAuthor;

    private OffsetDateTime updated;

    /**
     * Gets the body as a string (backward compatible method).
     * This works for API v2 where body is a plain string.
     * For API v3 (ADF format), this returns null - use {@link #getBodyAsDocument()} instead.
     *
     * @return The body as string for v2, null for v3
     */
    public String getBody() {
        return bodyInternal instanceof String ? (String) bodyInternal : null;
    }

    /**
     * Sets the body field.
     *
     * @param body The body value (String or Document)
     */
    @JsonSetter("body")
    public void setBody(Object body) {
        this.bodyInternal = body;
    }

    /**
     * Gets the body as an Atlassian Document Format (ADF) document.
     * This works for API v3 where body is an ADF object.
     * For API v2 (plain string), this returns null - use {@link #getBody()} instead.
     *
     * @return The body as Document for v3, null for v2
     */
    @JsonIgnore
    public Document getBodyAsDocument() {
        if (bodyInternal == null || bodyInternal instanceof String) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(bodyInternal, Document.class);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Creates a new comment.
     *
     * @param content The comment content
     * @return The corresponding comment
     */
    public static Comment from(String content) {
        Comment comment = new Comment();
        comment.setBody(content);
        return comment;
    }

    @Override
    public int compareTo(Comment other) {
        return getCreated().compareTo(other.getCreated());
    }

}
