package com.chavaillaz.jira.domain;

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
public class Users implements List<User> {

    @Delegate
    @JsonMerge
    private final List<User> users = new ArrayList<>();

    /**
     * Creates a new list of users.
     *
     * @param users The users to include in the list
     * @return The corresponding list of users
     */
    public static Users from(User... users) {
        return from(List.of(users));
    }

    /**
     * Creates a new list of users.
     *
     * @param users The users to include in the list
     * @return The corresponding list of users
     */
    public static Users from(List<User> users) {
        Users container = new Users();
        container.addAll(users);
        return container;
    }

}
