package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonFields {

    @JsonProperty("issuetype")
    private IssueType issueType;

    private Priority priority;

    private Status status;

    private String summary;

}
