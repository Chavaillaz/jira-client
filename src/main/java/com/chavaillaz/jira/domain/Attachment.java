package com.chavaillaz.jira.domain;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {

    private User author;

    private String content;

    private OffsetDateTime created;

    private String filename;

    private String id;

    private String mimeType;

    private String self;

    private Long size;

    private String thumbnail;

}
