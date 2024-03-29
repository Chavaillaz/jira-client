package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkLogs extends Result implements List<WorkLog> {

    @Delegate
    @JsonMerge
    private final List<WorkLog> workLogs = new ArrayList<>();

    /**
     * Creates a new list of work logs.
     *
     * @param workLogs The work logs to include in the list
     * @return The created list of work logs
     */
    public static WorkLogs from(WorkLog... workLogs) {
        return from(List.of(workLogs));
    }

    /**
     * Creates a new list of work logs.
     *
     * @param workLogs The work logs to include in the list
     * @return The created list of work logs
     */
    public static WorkLogs from(List<WorkLog> workLogs) {
        WorkLogs container = new WorkLogs();
        container.addAll(workLogs);
        return container;
    }

}
