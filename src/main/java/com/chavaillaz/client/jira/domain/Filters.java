package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonFormat(shape = ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filters extends Result implements List<Filter> {

    @Delegate
    @JsonMerge
    private final List<Filter> filters = new ArrayList<>();

    /**
     * Creates a new list of filters.
     *
     * @param filters The filters to include in the list
     * @return The corresponding list of filters
     */
    public static Filters from(Filter... filters) {
        return from(List.of(filters));
    }

    /**
     * Creates a new list of filters.
     *
     * @param filters The filters to include in the list
     * @return The corresponding list of filters
     */
    public static Filters from(List<Filter> filters) {
        Filters container = new Filters();
        container.addAll(filters);
        return container;
    }

}
