package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTracking {

    private String originalEstimate;

    private Integer originalEstimateSeconds;

    private String remainingEstimate;

    private Integer remainingEstimateSeconds;

}
