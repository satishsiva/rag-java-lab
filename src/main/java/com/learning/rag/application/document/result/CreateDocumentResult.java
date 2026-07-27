package com.learning.rag.application.document.result;

import com.learning.rag.domain.document.DocumentStatus;

import java.util.UUID;

public record CreateDocumentResult(
        UUID id,
        UUID knowledgeBaseId,
        String title,
        DocumentStatus status

) {
}