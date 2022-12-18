package com.chavaillaz.jira.domain;

import lombok.Data;

@Data
public class Identity implements Comparable<Identity> {

    private String id;

    private String key;

    private String name;

    private String self;

    @Override
    public int compareTo(Identity object) {
        return this.name.compareTo(object.getName());
    }

}