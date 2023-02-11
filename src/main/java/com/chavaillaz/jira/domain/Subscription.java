package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription {

    private Group group;

    private int id;

    private User user;

    /**
     * Creates a new subscription for a given group.
     *
     * @param group The group
     * @return The corresponding subscription
     */
    public static Subscription fromGroup(Group group) {
        Subscription subscription = new Subscription();
        subscription.setGroup(group);
        return subscription;
    }

    /**
     * Creates a new subscription for a given user.
     *
     * @param user The user
     * @return The corresponding subscription
     */
    public static Subscription fromUser(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        return subscription;
    }

}
