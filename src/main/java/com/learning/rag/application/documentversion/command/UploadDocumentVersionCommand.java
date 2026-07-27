package com.learning.rag.application.documentversion.command;
import java.io.InputStream;
import java.util.UUID;


public record  UploadDocumentVersionCommand (
        UUID documentId,

        String originalFileName,

        String contentType,

        long fileSize,

        InputStream inputStream

) {
}
