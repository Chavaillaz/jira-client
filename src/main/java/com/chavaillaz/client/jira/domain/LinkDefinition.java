package com.chavaillaz.client.jira.domain;

/**
 * Represents a link type between two issues.
 *
 * @param <T> The enumeration implementing it
 */
public interface LinkDefinition<T extends Enum<T>> {

    /**
     * Gets the identifier of the link.
     *
     * @return The link identifier
     */
    String getId();

    /**
     * Gets the name of the link.
     * Note that it can change depending on the user language.
     *
     * @return The link name
     */
    String getName();

    /**
     * Gets the name of the inward link.
     * Note that it can change depending on the user language.
     *
     * @return The inward name
     */
    String getInwardName();

    /**
     * Gets the name of the outward link.
     * Note that it can change depending on the user language.
     *
     * @return The outward name
     */
    String getOutwardName();

}