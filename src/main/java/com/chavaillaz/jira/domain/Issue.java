package com.chavaillaz.jira.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue extends BasicIssue {

    private ChangeLog changelog;

    private Fields fields;

    private List<Transition> transitions;

    /**
     * Creates a new issue.
     *
     * @param key The issue key
     * @return The corresponding issue
     */
    public static Issue fromKey(String key) {
        Issue issue = new Issue();
        issue.setKey(key);
        issue.setFields(new Fields());
        return issue;
    }

    /**
     * Creates a new issue
     *
     * @param type    The issue type
     * @param project The project key
     * @param summary The issue summary
     * @return The corresponding issue
     */
    public static Issue from(String type, String project, String summary) {
        Issue issue = new Issue();
        Fields fields = new Fields();
        fields.setIssueType(IssueType.fromName(type));
        fields.setProject(Project.fromKey(project));
        fields.setSummary(summary);
        issue.setFields(fields);
        return issue;
    }

}
