package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issues extends Result implements List<Issue> {

    @Delegate
    @JsonMerge
    private final List<Issue> issues = new ArrayList<>();

    private String expand;

}
