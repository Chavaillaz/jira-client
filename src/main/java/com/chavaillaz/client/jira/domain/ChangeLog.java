package com.chavaillaz.client.jira.domain;

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
public class ChangeLog extends Result implements List<History> {

    @Delegate
    @JsonMerge
    private final List<History> histories = new ArrayList<>();

    /**
     * Creates a new change log made up of issue updates.
     *
     * @param histories The updates to include in the list
     * @return The corresponding change log
     */
    public static ChangeLog from(History... histories) {
        return from(List.of(histories));
    }

    /**
     * Creates a new change log made up of issue updates.
     *
     * @param histories The updates to include in the list
     * @return The corresponding change log
     */
    public static ChangeLog from(List<History> histories) {
        ChangeLog container = new ChangeLog();
        container.addAll(histories);
        return container;
    }

}
