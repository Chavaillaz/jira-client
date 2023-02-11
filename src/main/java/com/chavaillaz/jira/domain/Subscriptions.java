package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscriptions implements List<User> {

    @Delegate
    @JsonMerge
    private final List<User> items = new ArrayList<>();

    @JsonProperty("end-index")
    private Integer endIndex;

    @JsonProperty("max-results")
    private Integer maxResults;

    private Integer size;

    @JsonIgnore
    private boolean empty;

    @JsonProperty("start-index")
    private Integer startIndex;

}
