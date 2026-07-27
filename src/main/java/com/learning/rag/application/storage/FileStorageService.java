package com.learning.rag.application.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface FileStorageService {

    String store(
            UUID documentId,
            UUID documentVersionId,
            String originalFileName,
            InputStream inputStream) throws IOException;

}