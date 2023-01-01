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

}
