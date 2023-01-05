package com.chavaillaz.jira.domain;

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
     * @param key The status key
     * @return The corresponding status
     */
    public static Status fromKey(String key) {
        Status status = new Status();
        status.setKey(key);
        return status;
    }

}
