package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Watchers implements List<User> {

    @Delegate
    @JsonMerge
    private final List<User> watchers = new ArrayList<>();

    private Boolean isWatching;

    private String self;

    private Integer watchCount;

    /**
     * Creates a new list of watchers.
     *
     * @param watchers The users to include in the list as watchers
     * @return The corresponding list of watchers
     */
    public static Watchers from(User... watchers) {
        return from(List.of(watchers));
    }

    /**
     * Creates a new list of watchers.
     *
     * @param watchers The users to include in the list as watchers
     * @return The corresponding list of watchers
     */
    public static Watchers from(List<User> watchers) {
        Watchers container = new Watchers();
        container.addAll(watchers);
        container.setWatchCount(watchers.size());
        return container;
    }

}
