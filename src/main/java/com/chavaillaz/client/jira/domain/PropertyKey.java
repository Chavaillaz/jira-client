package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyKey {

  private String key;

  private String self;
}
