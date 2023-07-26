package com.chavaillaz.jira.domain;

import static com.chavaillaz.jira.client.JiraConstants.defaultObjectMapper;

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
    private Attachments attachments;

    @JsonProperty("comment")
    private Comments comments;

    private Components components;

    private OffsetDateTime created;

    private User creator;

    @JsonIgnore
    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> customFields = new HashMap<>();

    private String description;

    private String dueDate;

    private String environment;

    private Versions fixVersions;

    @JsonProperty("issuelinks")
    private List<Link> issueLinks;

    private List<String> labels;

    private String lastViewed;

    private BasicIssue parent;

    private Progress progress;

    private Project project;

    private User reporter;

    private Resolution resolution;

    private OffsetDateTime resolutionDate;

    @JsonProperty("subtasks")
    private List<BasicIssue> subTasks;

    @JsonProperty("timeestimate")
    private Integer timeEstimate;

    @JsonProperty("timeoriginalestimate")
    private Integer timeOriginalEstimate;

    private Integer timeSpent;

    @JsonProperty("timetracking")
    private TimeTracking timeTracking;

    private OffsetDateTime updated;

    private Versions versions;

    private Votes votes;

    private Watchers watches;

    private WorkLogs workLogs;

    private Long workRatio;

    /**
     * Gets a custom field with the given key.
     * Note that a field can only be retrieved here if not already defined in the class itself.
     *
     * @param key The custom field key
     * @return The custom field value
     */
    public Object getCustomField(String key) {
        return this.customFields.get(key);
    }

    /**
     * Gets a custom field with the given key and casts it directly to the given type, if possible.
     * If the field type does not correspond to the given one, a {@code null} value will be returned.
     * Note that a field can only be retrieved here if not already defined in the class itself.
     *
     * @param key  The custom field key
     * @param type The custom field class
     * @param <T>  The custom field type
     * @return The custom field value
     * @throws IllegalArgumentException If conversion fails due to incompatible type
     */
    public <T> T getCustomField(String key, Class<T> type) {
        return defaultObjectMapper().convertValue(this.customFields.get(key), type);
    }

    /**
     * Sets a custom field with the given key and value.
     * If a previous value has been set for the given field, the old value is replaced by the given one.
     * Note that a field can only be set here if not already defined in the class itself.
     *
     * @param key   The custom field key
     * @param value The custom field value
     */
    public void putCustomField(String key, Object value) {
        this.customFields.put(key, value);
    }

}
