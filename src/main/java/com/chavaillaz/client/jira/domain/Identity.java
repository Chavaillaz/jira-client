package com.chavaillaz.client.jira.domain;

import lombok.Data;

@Data
public class Identity implements Comparable<Identity> {

    private String id;

    private String key;

    private String name;

    private String self;

    @Override
    public int compareTo(Identity other) {
        return getName().compareTo(other.getName());
    }

}