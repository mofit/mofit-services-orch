package com.mofit.orch.services.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    // TODO: Remove this because it's uploading the file to the local machine
    public static Resource getUserFileResource(MultipartFile file) throws IOException {
        //todo replace tempFile with a real file
        Path tempFile = Files.createTempFile("tempImageFile", ".txt");
        Files.write(tempFile, file.getBytes());
        System.out.println("uploading: " + tempFile);
        File fileToReturn = tempFile.toFile();
        //to upload in-memory bytes use ByteArrayResource instead
        return new FileSystemResource(fileToReturn);
    }
}
