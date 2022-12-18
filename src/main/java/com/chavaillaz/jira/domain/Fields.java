package com.chavaillaz.jira.domain;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fields extends CommonFields {

    private Progress aggregateProgress;

    @JsonProperty("aggregatetimeestimate")
    private Integer aggregateTimeEstimate;

    @JsonProperty("aggregatetimeoriginalestimate")
    private Integer aggregateTimeOriginalEstimate;

    private Integer aggregateTimeSpent;

    private User assignee;

    @JsonProperty("attachment")
    private List<Attachment> attachments;

    @JsonProperty("comment")
    private Comments comments;

    private List<Component> components;

    private OffsetDateTime created;

    private User creator;

    @JsonIgnore
    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> customFields = new HashMap<>();

    private String description;

    private String dueDate;

    private String environment;

    private List<Version> fixVersions;

    @JsonProperty("issuelinks")
    private List<Link> issueLinks;

    private List<String> labels;

    private String lastViewed;

    private Issue parent;

    private Progress progress;

    private Project project;

    private User reporter;

    private Resolution resolution;

    private OffsetDateTime resolutionDate;

    @JsonProperty("subtasks")
    private List<Issue> subTasks;

    @JsonProperty("timeestimate")
    private Integer timeEstimate;

    @JsonProperty("timeoriginalestimate")
    private Integer timeOriginalEstimate;

    private Integer timeSpent;

    @JsonProperty("timetracking")
    private TimeTracking timeTracking;

    private OffsetDateTime updated;

    private List<Version> versions;

    private Votes votes;

    private Watchers watches;

    private WorkLogs workLogs;

    private Integer workRatio;

}
