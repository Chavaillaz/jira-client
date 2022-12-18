package com.chavaillaz.jira.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SharedUsers {

    @JsonMerge
    private final List<User> items = new ArrayList<>();

    @JsonProperty("end-index")
    private Integer endIndex;

    @JsonProperty("max-results")
    private Integer maxResults;

    private Integer size;

    @JsonProperty("start-index")
    private Integer startIndex;

}
