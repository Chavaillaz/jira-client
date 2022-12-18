package com.chavaillaz.jira.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueType extends Identity {

    private Integer avatarId;

    private String description;

    private String iconUrl;

    private List<Status> statuses = new ArrayList<>();

    private Boolean subtask;

    /**
     * Creates a new issue type.
     *
     * @param name The issue type name
     * @return The corresponding issue type
     */
    public static IssueType fromName(String name) {
        IssueType issueType = new IssueType();
        issueType.setName(name);
        return issueType;
    }

}
