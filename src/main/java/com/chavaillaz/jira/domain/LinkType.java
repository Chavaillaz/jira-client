package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkType {

    private String id;

    private String inward;

    private String name;

    private String outward;

    private String self;

    /**
     * Creates a new link type.
     *
     * @param id The link type identifier
     * @return The corresponding link type
     */
    public static LinkType fromId(String id) {
        LinkType linkType = new LinkType();
        linkType.setId(id);
        return linkType;
    }

    /**
     * Creates a new link type.
     *
     * @param name The link type name
     * @return The corresponding link type
     */
    public static LinkType fromName(String name) {
        LinkType linkType = new LinkType();
        linkType.setName(name);
        return linkType;
    }

}
