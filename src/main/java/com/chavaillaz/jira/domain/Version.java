package com.chavaillaz.jira.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Version extends Identity {

    private Boolean archived;

    private String description;

    private Boolean overdue;

    private Integer projectId;

    private LocalDate releaseDate;

    private Boolean released;

}
