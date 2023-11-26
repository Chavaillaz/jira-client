package com.chavaillaz.client.jira.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueType extends Identity {

    private Integer avatarId;

    private String description;

    private String iconUrl;

    private List<Status> statuses;

    private Boolean subtask;

    /**
     * Creates a new issue type.
     *
     * @param id The issue type identifier
     * @return The corresponding issue type
     */
    public static IssueType fromId(String id) {
        IssueType issueType = new IssueType();
        issueType.setId(id);
        issueType.setStatuses(new ArrayList<>());
        return issueType;
    }

    /**
     * Creates a new issue type.
     *
     * @param name The issue type name
     * @return The corresponding issue type
     */
    public static IssueType fromName(String name) {
        IssueType issueType = new IssueType();
        issueType.setName(name);
        issueType.setStatuses(new ArrayList<>());
        return issueType;
    }

    /**
     * Creates a new issue type.
     *
     * @param id   The issue type identifier
     * @param name The issue type name
     * @return The corresponding issue type
     */
    public static IssueType from(String id, String name) {
        IssueType issueType = new IssueType();
        issueType.setId(id);
        issueType.setName(name);
        issueType.setStatuses(new ArrayList<>());
        return issueType;
    }

}
