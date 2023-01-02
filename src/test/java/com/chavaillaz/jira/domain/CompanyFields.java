package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyFields extends Fields {

    @JsonProperty("customfield_10000")
    private String team;

    @JsonProperty("customfield_10001")
    private User businessEngineer;

    @JsonProperty("customfield_10002")
    private User projectManager;

    /**
     * Option field (value to be selected from a list of possibilities)
     */
    @JsonProperty("customfield_10003")
    private FieldWrapper urgency;

    /**
     * XRay test execution (needs specific Jira plugin)
     */
    @JsonProperty("customfield_10004")
    private TestExecution testExecution;

}
