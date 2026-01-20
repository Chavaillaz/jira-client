package com.chavaillaz.client.jira.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Represents an Atlassian Document Format (ADF) document used in JIRA API v3.
 * This format is used for rich text fields like description and environment.
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    /**
     * The document type (typically "doc")
     */
    private String type;

    /**
     * The document version
     */
    private Integer version;

    /**
     * The document content nodes
     */
    private List<Object> content;

}

