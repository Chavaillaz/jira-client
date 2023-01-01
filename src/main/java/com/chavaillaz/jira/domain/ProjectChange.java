package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectChange extends Identity {

    private AssigneeType assigneeType;

    private Integer avatarId;

    private Integer categoryId;

    private String description;

    private Integer issueSecurityScheme;

    private User lead;

    private Integer notificationScheme;

    private Integer permissionScheme;

    private String projectTemplateKey;

    private String projectTypeKey;

}
