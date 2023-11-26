package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicIssue extends Identity {

    private CommonFields fields;

    /**
     * Creates a new basic issue (with limited fields).
     *
     * @param key The issue key
     * @return The corresponding basic issue
     */
    public static BasicIssue fromKey(String key) {
        BasicIssue issue = new BasicIssue();
        issue.setKey(key);
        issue.setFields(new CommonFields());
        return issue;
    }

}
