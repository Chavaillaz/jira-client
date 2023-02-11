package com.chavaillaz.jira.domain;

import static java.time.OffsetDateTime.now;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkLog implements Comparable<WorkLog> {

    private User author;

    private String comment;

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
