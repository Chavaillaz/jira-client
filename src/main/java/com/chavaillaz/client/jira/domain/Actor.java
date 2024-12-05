package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor {

    private String avatarUrl;

    private String displayName;

    private Integer id;

    private String name;

    private String type;

}
