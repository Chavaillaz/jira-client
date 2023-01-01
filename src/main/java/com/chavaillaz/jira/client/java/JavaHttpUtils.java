package com.chavaillaz.jira.client.java;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.experimental.UtilityClass;

/**
 * Utilities for Java HTTP Client.
 */
@UtilityClass
public class JavaHttpUtils {

    /**
     * Creates a body publisher for a multipart content.
     *
     * @param data     The data to store
     * @param boundary The boundary to separate them
     * @param charset  The charset to use
     * @return The corresponding body publisher
     * @throws IOException if an error occurs when reading files if given in the data parameter
     */
    public static HttpRequest.BodyPublisher ofMimeMultipartData(Map<Object, Object> data, String boundary, Charset charset) throws IOException {
        List<byte[]> byteArrays = new ArrayList<>();
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(charset);
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            byteArrays.add(separator);

            // If value is type of Path (file) append content type with file name and file binaries, otherwise simply append key=value
            if (entry.getValue() instanceof Path path) {
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName() + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(charset));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(charset));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n").getBytes(charset));
            }
        }

        byteArrays.add(("--" + boundary + "--").getBytes(charset));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }

    /**
     * Creates the {@link Map} for {@link #ofMimeMultipartData(Map, String, Charset)} with the given files.
     *
     * @param files The files
     * @return The filled map
     */
    public static Map<Object, Object> multipartWithFiles(File... files) {
        Map<Object, Object> data = new LinkedHashMap<>();
        for (File file : files) {
            data.put("file", file.toPath());
        }
        return data;
    }

}
