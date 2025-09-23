package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityProperty {

  private String key;

  private PropertyValue value;
}
