package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Votes implements List<User> {

    @Delegate
    @JsonMerge
    private final List<User> voters = new ArrayList<>();

    private Boolean hasVoted;

    private String self;

    private Integer votes;

}
