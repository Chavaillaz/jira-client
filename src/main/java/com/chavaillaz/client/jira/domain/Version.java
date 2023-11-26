package com.chavaillaz.client.jira.domain;

import static java.lang.module.ModuleDescriptor.Version.parse;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Version extends Identity {

    private Boolean archived;

    private String description;

    private Boolean overdue;

    private Integer projectId;

    private LocalDate releaseDate;

    private Boolean released;

    /**
     * Creates a new version.
     *
     * @param id The version identifier
     * @return The corresponding version
     */
    public static Version fromId(String id) {
        Version version = new Version();
        version.setId(id);
        return version;
    }

    /**
     * Creates a new version.
     *
     * @param name The version name
     * @return The corresponding version
     */
    public static Version fromName(String name) {
        Version version = new Version();
        version.setName(name);
        return version;
    }

    /**
     * Creates a new version.
     *
     * @param id   The version identifier
     * @param name The version name
     * @return The corresponding version
     */
    public static Version from(String id, String name) {
        Version version = new Version();
        version.setId(id);
        version.setName(name);
        return version;
    }

    @Override
    public int compareTo(Identity other) {
        try {
            var version = parse(this.getName());
            var versionOther = parse(other.getName());
            return version.compareTo(versionOther);
        } catch (Exception e) {
            return super.compareTo(other);
        }
    }

}
