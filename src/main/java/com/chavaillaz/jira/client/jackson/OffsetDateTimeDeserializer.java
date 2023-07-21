package com.chavaillaz.jira.client.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Override
    public OffsetDateTime deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        String dateString = jsonParser.getText();
        return OffsetDateTime.parse(dateString, formatter);
    }
}
