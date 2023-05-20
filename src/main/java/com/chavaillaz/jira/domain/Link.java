package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

    private String id;

    private BasicIssue inwardIssue;

    private BasicIssue outwardIssue;

    private String self;

    private LinkType type;

    /**
     * Creates a new issue link.
     *
     * @param type         The link type name
     * @param inwardIssue  The inward issue key
     * @param outwardIssue The outward issue key
     * @return The corresponding link
     */
    public static Link from(String type, String inwardIssue, String outwardIssue) {
        return from(LinkType.fromName(type), BasicIssue.fromKey(inwardIssue), BasicIssue.fromKey(outwardIssue));
    }

    /**
     * Creates a new issue link.
     *
     * @param type         The link type
     * @param inwardIssue  The inward issue key
     * @param outwardIssue The outward issue key
     * @return The corresponding link
     */
    public static Link from(LinkType type, String inwardIssue, String outwardIssue) {
        return from(type, BasicIssue.fromKey(inwardIssue), BasicIssue.fromKey(outwardIssue));
    }

    /**
     * Creates a new issue link.
     *
     * @param type         The link type
     * @param inwardIssue  The inward issue
     * @param outwardIssue The outward issue
     * @return The corresponding link
     */
    public static Link from(LinkType type, BasicIssue inwardIssue, BasicIssue outwardIssue) {
        Link link = new Link();
        link.setType(type);
        link.setInwardIssue(inwardIssue);
        link.setOutwardIssue(outwardIssue);
        return link;
    }

}
