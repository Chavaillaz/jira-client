package com.chavaillaz.jira.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Roles implements Map<String, String> {

    @Delegate
    @JsonMerge
    private final Map<String, String> roles = new HashMap<>();

}
