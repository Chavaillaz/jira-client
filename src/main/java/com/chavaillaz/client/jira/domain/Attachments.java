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
public class Attachments implements List<Attachment> {

    @Delegate
    @JsonMerge
    private final List<Attachment> attachments = new ArrayList<>();

    /**
     * Creates a new list of attachments.
     *
     * @param attachments The attachments to include in the list
     * @return The corresponding list of attachments
     */
    public static Attachments from(Attachment... attachments) {
        return from(List.of(attachments));
    }

    /**
     * Creates a new list of attachments.
     *
     * @param attachments The attachments to include in the list
     * @return The corresponding list of attachments
     */
    public static Attachments from(List<Attachment> attachments) {
        Attachments container = new Attachments();
        container.addAll(attachments);
        return container;
    }

}
