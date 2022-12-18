package com.chavaillaz.jira.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Part of the XRay plugin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestExecution {

    private int count;

    private List<TestStatus> statuses;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestStatus {

        private String color;

        private String description;

        private int id;

        private boolean isFinal;

        private boolean isNative;

        private String name;

        private int statusCount;

        private float statusPercent;

    }

}
