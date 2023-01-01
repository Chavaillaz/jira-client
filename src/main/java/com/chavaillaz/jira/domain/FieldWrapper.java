package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldWrapper {

    private String disabled;

    private String id;

    private String self;

    private String value;

    /**
     * Creates a new field wrapper.
     *
     * @param value The field value
     * @return The corresponding field wrapper
     */
    public static FieldWrapper from(String value) {
        FieldWrapper wrapper = new FieldWrapper();
        wrapper.setValue(value);
        return wrapper;
    }

}