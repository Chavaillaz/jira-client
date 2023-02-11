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
public class Comments extends Result implements List<Comment> {

    @Delegate
    @JsonMerge
    private final List<Comment> comments = new ArrayList<>();

    /**
     * Creates a new list of comments.
     *
     * @param comments The comments to include in the list
     * @return The corresponding list of comments
     */
    public static Comments from(Comment... comments) {
        return from(List.of(comments));
    }

    /**
     * Creates a new list of comments.
     *
     * @param comments The comments to include in the list
     * @return The corresponding list of comments
     */
    public static Comments from(List<Comment> comments) {
        Comments container = new Comments();
        container.addAll(comments);
        return container;
    }

}
