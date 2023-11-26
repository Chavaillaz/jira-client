package com.chavaillaz.client.jira.jackson;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.chavaillaz.client.jira.JiraConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.format(JiraConstants.DATE_TIME_FORMATTER));
    }

}
