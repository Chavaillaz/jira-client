package com.chavaillaz.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends Identity {

    private Boolean active;

    private Avatars avatarUrls;

    private String displayName;

    private String emailAddress;

    private String timeZone;

    /**
     * Creates a new user.
     *
     * @param key The user key
     * @return The corresponding user
     */
    public static User fromKey(String key) {
        User user = new User();
        user.setKey(key);
        return user;
    }

    /**
     * Creates a new user.
     *
     * @param name The user name
     * @return The corresponding user
     */
    public static User fromName(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

}
