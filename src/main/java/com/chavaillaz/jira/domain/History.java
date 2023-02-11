package com.chavaillaz.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.time.OffsetDateTime;
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
public class History implements List<HistoryItem> {

    @Delegate
    @JsonMerge
    private final List<HistoryItem> items = new ArrayList<>();

    private User author;

    private OffsetDateTime created;

    private String id;

    /**
     * Creates a new list of history items corresponding to updates in an issue.
     *
     * @param historyItems The updates to include in the list
     * @return The corresponding list of updates
     */
    public static History from(HistoryItem... historyItems) {
        return from(List.of(historyItems));
    }

    /**
     * Creates a new list of history items corresponding to updates in an issue.
     *
     * @param historyItems The updates to include in the list
     * @return The corresponding list of updates
     */
    public static History from(List<HistoryItem> historyItems) {
        History container = new History();
        container.addAll(historyItems);
        return container;
    }

}
