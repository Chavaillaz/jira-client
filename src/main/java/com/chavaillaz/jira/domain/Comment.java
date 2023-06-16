package com.chavaillaz.jira.domain;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Comparable<Comment> {

    private User author;

    private String body;

    private String renderedBody;

    private OffsetDateTime created;

    private String id;

    private String self;

    private User updateAuthor;

    private OffsetDateTime updated;

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
