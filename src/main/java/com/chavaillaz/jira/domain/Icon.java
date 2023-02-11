package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Icon {

    private IconProperties properties;

    private String title;

    private String type;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IconProperties {

        private String link;

        private String title;

        private String url16x16;

    }

}
