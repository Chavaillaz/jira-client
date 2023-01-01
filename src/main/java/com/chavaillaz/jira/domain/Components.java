package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Components implements List<Component> {

    @Delegate
    @JsonMerge
    private final List<Component> components = new ArrayList<>();

}
