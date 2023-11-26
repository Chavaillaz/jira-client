package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessages implements List<String> {

    @Delegate
    @JsonMerge
    private final List<String> errorMessages = new ArrayList<>();

    /**
     * Used by Jackson when reading fields that are not declared in the class.
     *
     * @param key   The field name
     * @param value The field value
     */
    @JsonAnySetter
    public void appendOtherErrors(String key, Object value) {
        if (value instanceof Map<?, ?> map) {
            map.forEach((k, v) -> errorMessages.add("(" + k + ") " + v));
        }
    }

}
