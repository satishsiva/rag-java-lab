package com.learning.rag.controller.documentversion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDocumentVersionRequest(

        @NotNull
        UUID documentId,

        @NotBlank
        String originalFileName,

        String storagePath,

        String checksum,

        @NotBlank
        String contentType,

        @NotNull
        Long fileSize

) {
}