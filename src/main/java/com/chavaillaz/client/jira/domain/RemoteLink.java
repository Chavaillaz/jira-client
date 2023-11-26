package com.chavaillaz.client.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteLink {

    private String globalId;

    private String id;

    private RemoteLinkObject object;

    private String relationship;

    private String self;

    /**
     * Creates a new remote link.
     *
     * @param relationship The relationship of the issue to this link
     * @param object       The remote link object
     * @return The corresponding remote link
     */
    public static RemoteLink from(String relationship, RemoteLinkObject object) {
        RemoteLink remoteLink = new RemoteLink();
        remoteLink.setRelationship(relationship);
        remoteLink.setObject(object);
        return remoteLink;
    }

    /**
     * Creates a new remote link.
     *
     * @param object The remote link object
     * @return The corresponding remote link
     */
    public static RemoteLink from(RemoteLinkObject object) {
        RemoteLink remoteLink = new RemoteLink();
        remoteLink.setObject(object);
        return remoteLink;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RemoteLinkObject {

        private Icon icon;

        private Status status;

        private String summary;

        private String title;

        private String url;

        /**
         * Creates a new remote link object.
         *
         * @param title The link title
         * @param url   The link URL
         * @return The corresponding remote link object
         */
        public static RemoteLinkObject from(String title, String url) {
            RemoteLinkObject object = new RemoteLinkObject();
            object.setTitle(title);
            object.setUrl(url);
            return object;
        }

    }

}
