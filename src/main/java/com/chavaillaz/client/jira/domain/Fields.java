package com.chavaillaz.client.jira.domain;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chavaillaz.client.jira.JiraConstants;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

    /**
     * Description field. Can be either:
     * - String (JIRA API v2)
     * - Document object (JIRA API v3 - Atlassian Document Format)
     *
     * Note: Use getDescription() for backward compatibility (returns String),
     * or getDescriptionAsDocument() for v3 Document format.
     */
    @JsonProperty("description")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Object descriptionInternal;

    @JsonProperty("duedate")
    private String dueDate;

    /**
     * Environment field. Can be either:
     * - String (JIRA API v2)
     * - Document object (JIRA API v3 - Atlassian Document Format)
     *
     * Note: Use getEnvironment() for backward compatibility (returns String),
     * or getEnvironmentAsDocument() for v3 Document format.
     */
    @JsonProperty("environment")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Object environmentInternal;

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
        return JiraConstants.jiraObjectMapper().convertValue(this.customFields.get(key), type);
    }

    /**
     * Gets the description as a String (backward compatible method).
     * In JIRA API v2, this is a plain String.
     * In JIRA API v3, this extracts plain text from the Document format.
     *
     * @return The description as a String, or null if not set
     */
    public String getDescription() {
        return convertToString(descriptionInternal);
    }

    /**
     * Sets the description field.
     *
     * @param description The description value (String or Document)
     */
    @JsonSetter("description")
    public void setDescription(Object description) {
        this.descriptionInternal = description;
    }

    /**
     * Gets the environment as a String (backward compatible method).
     * In JIRA API v2, this is a plain String.
     * In JIRA API v3, this extracts plain text from the Document format.
     *
     * @return The environment as a String, or null if not set
     */
    public String getEnvironment() {
        return convertToString(environmentInternal);
    }

    /**
     * Sets the environment field.
     *
     * @param environment The environment value (String or Document)
     */
    @JsonSetter("environment")
    public void setEnvironment(Object environment) {
        this.environmentInternal = environment;
    }

    /**
     * Gets the description as a String.
     * In JIRA API v2, this is a plain String.
     * In JIRA API v3, this extracts plain text from the Document format.
     * (Same as getDescription())
     *
     * @return The description as a String, or null if not set
     */
    @JsonIgnore
    public String getDescriptionAsString() {
        return convertToString(descriptionInternal);
    }

    /**
     * Gets the environment as a String.
     * In JIRA API v2, this is a plain String.
     * In JIRA API v3, this extracts plain text from the Document format.
     * (Same as getEnvironment())
     *
     * @return The environment as a String, or null if not set
     */
    @JsonIgnore
    public String getEnvironmentAsString() {
        return convertToString(environmentInternal);
    }

    /**
     * Gets the description as a Document object (only applicable for JIRA API v3).
     *
     * @return The description as a Document, or null if not set or not in ADF format
     */
    @JsonIgnore
    public Document getDescriptionAsDocument() {
        return convertToDocument(descriptionInternal);
    }

    /**
     * Gets the environment as a Document object (only applicable for JIRA API v3).
     *
     * @return The environment as a Document, or null if not set or not in ADF format
     */
    @JsonIgnore
    public Document getEnvironmentAsDocument() {
        return convertToDocument(environmentInternal);
    }

    /**
     * Converts a field value to String.
     * If it's already a String, returns it as is.
     * If it's a Document (v3), attempts to extract plain text from content.
     *
     * @param value The field value
     * @return The string representation, or null
     */
    private String convertToString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        // For v3 Document format, extract plain text from the ADF structure
        Document doc = convertToDocument(value);
        if (doc != null) {
            return extractPlainText(doc);
        }
        return null;
    }

    /**
     * Extracts plain text from an Atlassian Document Format (ADF) document.
     * Recursively traverses the content nodes and concatenates text.
     *
     * @param doc The Document object
     * @return The extracted plain text, or empty string if no text found
     */
    private String extractPlainText(Document doc) {
        if (doc == null || doc.getContent() == null) {
            return "";
        }
        StringBuilder text = new StringBuilder();
        extractTextFromNodes(doc.getContent(), text);
        return text.toString().trim();
    }

    /**
     * Recursively extracts text from content nodes.
     *
     * @param nodes The content nodes
     * @param text  The StringBuilder to append text to
     */
    @SuppressWarnings("unchecked")
    private void extractTextFromNodes(List<Object> nodes, StringBuilder text) {
        if (nodes == null) {
            return;
        }

        for (Object node : nodes) {
            if (node instanceof Map) {
                Map<String, Object> nodeMap = (Map<String, Object>) node;
                String type = (String) nodeMap.get("type");

                if ("text".equals(type)) {
                    // Text node - extract the text value
                    String textValue = (String) nodeMap.get("text");
                    if (textValue != null) {
                        text.append(textValue);
                    }
                } else if ("hardBreak".equals(type)) {
                    // Hard break - add a newline
                    text.append("\n");
                } else {
                    // Other node types (paragraph, heading, etc.) - process children
                    Object content = nodeMap.get("content");
                    if (content instanceof List) {
                        extractTextFromNodes((List<Object>) content, text);
                    }

                    // Add spacing between block-level elements
                    if (isBlockNode(type) && !text.isEmpty() && !text.toString().endsWith("\n")) {
                        text.append("\n");
                    }
                }
            }
        }
    }

    /**
     * Checks if a node type is a block-level element.
     *
     * @param type The node type
     * @return true if it's a block-level element
     */
    private boolean isBlockNode(String type) {
        return "paragraph".equals(type) ||
               "heading".equals(type) ||
               "bulletList".equals(type) ||
               "orderedList".equals(type) ||
               "codeBlock".equals(type) ||
               "blockquote".equals(type);
    }

    /**
     * Converts a field value to Document.
     * If it's a Map or Document object, converts it to Document.
     *
     * @param value The field value
     * @return The Document object, or null
     */
    private Document convertToDocument(Object value) {
        if (value == null || value instanceof String) {
            return null;
        }
        try {
            return JiraConstants.jiraObjectMapper().convertValue(value, Document.class);
        } catch (Exception e) {
            return null;
        }
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
