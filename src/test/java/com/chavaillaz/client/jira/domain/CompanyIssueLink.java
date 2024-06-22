package com.chavaillaz.client.jira.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyIssueLink implements LinkDefinition<CompanyIssueLink> {

    DELIVER("1234", "Delivers", "is delivered with", "delivers"),
    TEST("4321", "Tests", "tested by", "tests");

    private final String id;
    private final String name;
    private final String inwardName;
    private final String outwardName;

}
