package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyFields extends Fields {

    @JsonProperty("customfield_51540")
    private String team;

    @JsonProperty("customfield_93440")
    private User businessEngineer;

    @JsonProperty("customfield_23143")
    private User projectManager;

    @JsonProperty("customfield_84462")
    private FieldWrapper urgency;

    @JsonProperty("customfield_52559")
    private TestExecution testExecution;

}
