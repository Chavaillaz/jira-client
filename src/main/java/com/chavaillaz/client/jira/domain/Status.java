package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status extends Identity {

    private String description;

    private String iconUrl;

    private StatusCategory statusCategory;

    private String statusColor;

    /**
     * Creates a new status.
     *
     * @param id The status identifier
     * @return The corresponding status
     */
    public static Status fromId(String id) {
        Status status = new Status();
        status.setId(id);
        return status;
    }

    /**
     * Creates a new status.
     *
     * @param name The status name
     * @return The corresponding status
     */
    public static Status fromName(String name) {
        Status status = new Status();
        status.setName(name);
        return status;
    }

    /**
     * Creates a new status.
     *
     * @param id   The status identifier
     * @param name The status name
     * @return The corresponding status
     */
    public static Status from(String id, String name) {
        Status status = new Status();
        status.setId(id);
        status.setName(name);
        return status;
    }

}
