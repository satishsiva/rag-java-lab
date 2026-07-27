package com.learning.rag.controller.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDocumentRequest(

        @NotNull
        UUID knowledgeBaseId,

        @NotBlank
        String title,

        @NotBlank
        String originalFileName,

        @NotBlank
        String contentType,

        @NotNull
        Long fileSize
) {
}