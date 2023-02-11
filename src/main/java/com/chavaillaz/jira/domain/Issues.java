package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

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
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issues<T extends Issue> extends Result implements List<T> {

    @Delegate
    @JsonMerge
    private final List<T> issues = new ArrayList<>();

    private String expand;

    /**
     * Creates a new list of issues.
     *
     * @param issues The issues to include in the list
     * @return The corresponding list of issues
     */
    public static <T extends Issue> Issues<T> from(T... issues) {
        return from(List.of(issues));
    }

    /**
     * Creates a new list of issues.
     *
     * @param issues The issues to include in the list
     * @return The corresponding list of issues
     */
    public static <T extends Issue> Issues<T> from(List<T> issues) {
        Issues<T> container = new Issues<>();
        container.addAll(issues);
        return container;
    }

}
