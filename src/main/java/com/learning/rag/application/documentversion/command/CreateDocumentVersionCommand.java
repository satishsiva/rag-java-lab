package com.learning.rag.application.documentversion.command;

import java.util.UUID;

public record CreateDocumentVersionCommand(

        UUID documentId,

        String originalFileName,

        String storagePath,

        String checksum,

        String contentType,

        Long fileSize

) {
}