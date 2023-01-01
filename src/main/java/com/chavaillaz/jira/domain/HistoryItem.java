package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryItem {

    private String field;

    private String fieldType;

    private String from;

    private String fromString;

    private String to;

    private String toString;

}
