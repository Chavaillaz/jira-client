package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonFormat(shape = ARRAY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteLinks extends Result implements List<RemoteLink> {

    @Delegate
    @JsonMerge
    private final List<RemoteLink> remoteLinks = new ArrayList<>();

    /**
     * Creates a new list of remote links.
     *
     * @param remoteLinks The remote links to include in the list
     * @return The corresponding list of remote links
     */
    public static RemoteLinks from(RemoteLink... remoteLinks) {
        return from(List.of(remoteLinks));
    }

    /**
     * Creates a new list of remote links.
     *
     * @param remoteLinks The remote links to include in the list
     * @return The corresponding list of remote links
     */
    public static RemoteLinks from(List<RemoteLink> remoteLinks) {
        RemoteLinks container = new RemoteLinks();
        container.addAll(remoteLinks);
        return container;
    }

}
