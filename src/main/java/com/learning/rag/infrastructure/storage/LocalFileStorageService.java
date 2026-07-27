package com.learning.rag.infrastructure.storage;

import com.learning.rag.application.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService
        implements FileStorageService {

    private final Path root;

    public LocalFileStorageService(
            @Value("${rag.storage.root}") String storageRoot) {

        this.root = Path.of(storageRoot);
    }

    @Override
    public String store(
            UUID documentId,
            UUID documentVersionId,
            String originalFileName,
            InputStream inputStream)
            throws IOException {

        Path directory = root
                .resolve(documentId.toString())
                .resolve(documentVersionId.toString());

        Files.createDirectories(directory);

        Path targetFile = directory.resolve(originalFileName);

        Files.copy(
                inputStream,
                targetFile,
                StandardCopyOption.REPLACE_EXISTING);

        return targetFile.toString();
    }
}