package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Avatars {

    @JsonProperty("16x16")
    private String format16x16;

    @JsonProperty("24x24")
    private String format24x24;

    @JsonProperty("32x32")
    private String format32x32;

    @JsonProperty("48x48")
    private String format48x48;

}
