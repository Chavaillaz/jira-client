package com.chavaillaz.jira.utils;

import static java.util.Optional.ofNullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    /**
     * Gets the content of an {@link InputStream} and convert it to a {@link String}.
     *
     * @param inputStream The input stream to read
     * @param charset     The charset to use to read the input stream
     * @return The {@link String} representing the content of the input stream
     * @throws IOException If some I/O error occurs when reading the input stream
     */
    public static String readInputStream(InputStream inputStream, Charset charset) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = inputStream.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        return result.toString(charset);
    }

    /**
     * Gets a property with priority from environment variable,
     * then from system properties or {@code null}.
     *
     * @param propertyKey The property key to search
     * @return The property value found (environment, system) or {@code null}
     */
    public static String getProperty(String propertyKey) {
        return getProperty(propertyKey, null);
    }

    /**
     * Gets a property with priority from environment variable,
     * then from system properties or defaulting to the given value.
     *
     * @param propertyKey  The property key to search
     * @param defaultValue The default value to use if no property was found
     * @return The property value found (environment, system) or the default given value
     */
    public static String getProperty(String propertyKey, String defaultValue) {
        return ofNullable(System.getenv(propertyKey))
                .or(() -> ofNullable(System.getProperty(propertyKey)))
                .orElse(defaultValue);
    }

}
