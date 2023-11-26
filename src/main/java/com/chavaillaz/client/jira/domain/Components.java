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
public class Components implements List<Component> {

    @Delegate
    @JsonMerge
    private final List<Component> components = new ArrayList<>();

    /**
     * Creates a new list of components.
     *
     * @param components The components to include in the list
     * @return The corresponding list of components
     */
    public static Components from(Component... components) {
        return from(List.of(components));
    }

    /**
     * Creates a new list of components.
     *
     * @param components The components to include in the list
     * @return The corresponding list of components
     */
    public static Components from(List<Component> components) {
        Components container = new Components();
        container.addAll(components);
        return container;
    }

}
