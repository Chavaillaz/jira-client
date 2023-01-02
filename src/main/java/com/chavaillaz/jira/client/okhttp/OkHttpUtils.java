package com.chavaillaz.jira.client.okhttp;

import static java.nio.file.Files.probeContentType;

import java.io.File;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Utilities for OkHttp Client.
 */
@UtilityClass
public class OkHttpUtils {

    /**
     * Generates a new multipart body with the given files.
     *
     * @param files The list of files to include
     * @return The multipart body
     */
    @SneakyThrows
    public static MultipartBody multipartWithFiles(File... files) {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (File file : files) {
            MediaType fileType = MediaType.parse(probeContentType(file.toPath()));
            multipartBuilder.addFormDataPart("file", file.getName(), RequestBody.create(file, fileType));
        }

        return multipartBuilder.build();
    }

}
