package com.chavaillaz.client.jira.jackson;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.chavaillaz.client.jira.JiraConstants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return OffsetDateTime.parse(jsonParser.getText(), JiraConstants.DATE_TIME_FORMATTER);
    }

}
