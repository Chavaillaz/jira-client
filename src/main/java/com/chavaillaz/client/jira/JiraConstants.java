package com.chavaillaz.client.jira;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chavaillaz.client.jira.domain.ErrorMessages;
import com.chavaillaz.client.jira.jackson.OffsetDateTimeDeserializer;
import com.chavaillaz.client.jira.jackson.OffsetDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JiraConstants {

    public static final String BASE_API = "/rest/api/2/";

    public static final String HEADER_ATLASSIAN_TOKEN = "X-Atlassian-Token";

    public static final String HEADER_ATLASSIAN_TOKEN_DISABLED = "no-check";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * Pattern to extract error messages, sometimes written by Jira in HTML format like:
     * <pre>
     *     {@code
     *     <div class="aui-message aui-message-warning warning">
     *         <p>Encountered a <code>&quot;401 - Unauthorized&quot;</code> error while loading this page.</p>
     *         <p>Basic Authentication Failure - Reason : AUTHENTICATED_FAILED</p>
     *         <p><a href="/secure/MyJiraHome.jspa">Go to Jira home</a></p>
     *     </div>
     *     }
     * </pre>
     */
    public static final Pattern ERRORS_HTML_PATTERN = Pattern.compile("<div class=\".*aui-message.*\">(?<errors>(.|\\n)*?)<\\/div>");

    /**
     * Creates an object mapper that will be used to serialize and deserialize all objects.
     *
     * @return The object mapper
     */
    public static ObjectMapper jiraObjectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .addModule(new SimpleModule()
                        .addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer())
                        .addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer()))
                .serializationInclusion(NON_NULL)
                .enable(ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    /**
     * Extracts errors from the given JSON returned by Jira.
     *
     * @param json The content from which extract errors
     * @return The extracted errors or {@code null} in case no one was detected
     * @see ErrorMessages
     */
    public static List<String> extractJsonErrors(String json) {
        try {
            return jiraObjectMapper().readValue(json, ErrorMessages.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extracts errors from the given HTML returned by Jira.
     *
     * @param html The content from which extract errors
     * @return The extracted errors or {@code null} in case no one was detected
     * @see #ERRORS_HTML_PATTERN
     */
    public static String extractHtmlErrors(String html) {
        return Optional.ofNullable(html)
                .map(ERRORS_HTML_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> normalizeSpace(matcher.group("errors")
                        .replaceAll("<[^>]*>", EMPTY)
                        .replaceAll("\\&[a-z]*\\;", EMPTY)))
                .orElse(null);
    }

    /**
     * Gets the names of all fields declared in a class type and its hierarchy.
     * The names are retrieved either by {@link JsonProperty#value()} or by {@link Field#getName()}.
     * Meant to be used to optimize calls to Jira in order to get only the attributes available in the given model.
     *
     * @param type The class type
     * @return The names of all declared fields
     */
    public static Set<String> getNameFields(Class<?> type) {
        return getDeepFields(type).stream()
                .map(field -> Optional.ofNullable(field.getAnnotation(JsonProperty.class))
                        .map(JsonProperty::value)
                        .orElse(field.getName()))
                .collect(toSet());
    }

    /**
     * Gathers all fields from the given type and all its hierarchy.
     *
     * @param type The class type
     * @return The fields, including the inherited ones
     */
    public static List<Field> getDeepFields(Class<?> type) {
        if (type == null) {
            return emptyList();
        }

        List<Field> deepFields = new ArrayList<>(getDeepFields(type.getSuperclass()));
        List<Field> fields = List.of(type.getDeclaredFields());
        deepFields.addAll(fields);
        return deepFields;
    }

}
