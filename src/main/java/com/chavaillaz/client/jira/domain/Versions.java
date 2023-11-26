package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Versions implements List<Version> {

    @Delegate
    @JsonMerge
    private final List<Version> versions = new ArrayList<>();

    /**
     * Creates a new list of versions.
     *
     * @param versions The versions to include in the list
     * @return The corresponding list of versions
     */
    public static Versions from(Version... versions) {
        return from(List.of(versions));
    }

    /**
     * Creates a new list of versions.
     *
     * @param versions The versions to include in the list
     * @return The corresponding list of versions
     */
    public static Versions from(List<Version> versions) {
        Versions container = new Versions();
        container.addAll(versions);
        return container;
    }

}
