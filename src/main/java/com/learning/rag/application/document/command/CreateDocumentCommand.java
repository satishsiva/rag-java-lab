package com.learning.rag.application.document.command;

import java.util.UUID;

public record CreateDocumentCommand(

        UUID knowledgeBaseId,

        String title,

        String originalFileName,

        String contentType,

        long fileSize

) {
}