package com.chavaillaz.client.jira.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyKeys {

  private final List<PropertyKey> keys = new ArrayList<>();
}
