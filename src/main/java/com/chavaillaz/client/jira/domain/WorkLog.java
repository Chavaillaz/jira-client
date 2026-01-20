package com.chavaillaz.client.jira.domain;

import static java.time.OffsetDateTime.now;

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
public class WorkLog implements Comparable<WorkLog> {

    private User author;

    /**
     * The worklog comment.
     * In API v2, this is a String.
     * In API v3, this is an Atlassian Document Format (ADF) object.
     */
    @JsonProperty("comment")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Object commentInternal;

    private String created;

    private String id;

    private String issueId;

    private String self;

    private OffsetDateTime started;

    private String timeSpent;

    private Integer timeSpentSeconds;

    private User updateAuthor;

    private OffsetDateTime updated;

    /**
     * Gets the comment as a string (backward compatible method).
     * This works for API v2 where comment is a plain string.
     * For API v3 (ADF format), this returns null - use {@link #getCommentAsDocument()} instead.
     *
     * @return The comment as string for v2, null for v3
     */
    public String getComment() {
        return commentInternal instanceof String ? (String) commentInternal : null;
    }

    /**
     * Sets the comment field.
     *
     * @param comment The comment value (String or Document)
     */
    @JsonSetter("comment")
    public void setComment(Object comment) {
        this.commentInternal = comment;
    }

    /**
     * Gets the comment as an Atlassian Document Format (ADF) document.
     * This works for API v3 where comment is an ADF object.
     * For API v2 (plain string), this returns null - use {@link #getComment()} instead.
     *
     * @return The comment as Document for v3, null for v2
     */
    @JsonIgnore
    public Document getCommentAsDocument() {
        if (commentInternal == null || commentInternal instanceof String) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(commentInternal, Document.class);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Creates a new work log with hours as temporal unit.
     * Its start date will be set as now.
     *
     * @param timeHours The number of hours spent for this work
     * @param comment   The comment on the work done
     * @return The corresponding work log
     */
    public static WorkLog fromHours(Double timeHours, String comment) {
        return fromHours(now(), timeHours, comment);
    }

    /**
     * Creates a new work log with hours as temporal unit.
     *
     * @param date      The start date of the work
     * @param timeHours The number of hours spent for this work
     * @param comment   The comment on the work done
     * @return The corresponding work log
     */
    public static WorkLog fromHours(OffsetDateTime date, Double timeHours, String comment) {
        WorkLog workLog = new WorkLog();
        workLog.setStarted(date);
        workLog.setTimeSpentSeconds((int) (timeHours * 60 * 60));
        workLog.setComment(comment);
        return workLog;
    }

    @Override
    public int compareTo(WorkLog other) {
        return getStarted().compareTo(other.getStarted());
    }

}
