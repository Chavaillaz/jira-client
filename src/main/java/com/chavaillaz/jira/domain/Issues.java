package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issues<T extends Issue> extends Result implements List<T> {

    @Delegate
    @JsonMerge
    private final List<T> issues = new ArrayList<>();

    private String expand;

}
