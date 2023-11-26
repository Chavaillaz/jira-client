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
public class UsersWrapper implements List<User> {

    @Delegate
    @JsonMerge
    private final List<User> items = new ArrayList<>();

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
     * Creates a new list of users.
     *
     * @param users The users to include in the list
     * @return The corresponding list of users
     */
    public static UsersWrapper from(User... users) {
        return from(List.of(users));
    }

    /**
     * Creates a new list of users.
     *
     * @param users The users to include in the list
     * @return The corresponding list of users
     */
    public static UsersWrapper from(List<User> users) {
        UsersWrapper container = new UsersWrapper();
        container.addAll(users);
        return container;
    }

}
