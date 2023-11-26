package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscriptions implements List<Subscription> {

    @Delegate
    @JsonMerge
    private final List<Subscription> items = new ArrayList<>();

    @JsonProperty("end-index")
    private Integer endIndex;

    @JsonProperty("max-results")
    private Integer maxResults;

    private Integer size;

    @JsonIgnore
    private boolean empty;

    @JsonProperty("start-index")
    private Integer startIndex;

    /**
     * Creates a new list of subscriptions.
     *
     * @param subscriptions The subscriptions to include in the list
     * @return The corresponding list of subscriptions
     */
    public static Subscriptions from(Subscription... subscriptions) {
        return from(List.of(subscriptions));
    }

    /**
     * Creates a new list of subscriptions.
     *
     * @param subscriptions The subscriptions to include in the list
     * @return The corresponding list of subscriptions
     */
    public static Subscriptions from(List<Subscription> subscriptions) {
        Subscriptions container = new Subscriptions();
        container.addAll(subscriptions);
        return container;
    }

}
