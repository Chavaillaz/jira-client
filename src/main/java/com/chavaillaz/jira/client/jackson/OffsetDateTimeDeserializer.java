package com.chavaillaz.jira.client.jackson;

import static com.chavaillaz.jira.client.JiraConstants.DATE_TIME_FORMATTER;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return OffsetDateTime.parse(jsonParser.getText(), DATE_TIME_FORMATTER);
    }

}
