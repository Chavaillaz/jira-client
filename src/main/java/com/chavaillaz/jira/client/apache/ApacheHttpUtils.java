package com.chavaillaz.jira.client.apache;

import static java.nio.file.Files.readAllBytes;
import static org.apache.hc.core5.http.ContentType.DEFAULT_BINARY;

import java.io.File;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.hc.client5.http.entity.mime.ByteArrayBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;

/**
 * Utilities for Apache HTTP Client.
 */
@UtilityClass
public class ApacheHttpUtils {

    /**
     * Generates a new multipart entity builder with the given files.
     *
     * @param files The list of files to include
     * @return The multipart entity builder
     */
    @SneakyThrows
    public static MultipartEntityBuilder multipartWithFiles(File... files) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setContentType(DEFAULT_BINARY);
        for (File file : files) {
            builder.addPart("file", new ByteArrayBody(readAllBytes(file.toPath()), file.getName()));
        }
        return builder;
    }

}
