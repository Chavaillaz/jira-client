package com.chavaillaz.client.jira.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyIssueStatus implements StatusDefinition<CompanyIssueStatus> {

    OPEN("1", "Open", List.of(
            new StatusTransition("1")
    )),
    DEVELOPED("2", "Developed", List.of(
            new StatusTransition(OPEN, "2")
    )),
    INSTALLED("3", "Installed", List.of(
            new StatusTransition(DEVELOPED, "3")
    )),
    CLOSED("4", "Closed", List.of(
            new StatusTransition(OPEN, "4"),
            new StatusTransition(INSTALLED, "5")
    ));

    private final String id;
    private final String name;
    private final List<StatusTransition> transitions;

}
