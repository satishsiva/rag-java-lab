package com.learning.rag.controller.document;

import java.util.UUID;

public record CreateDocumentResponse(

        UUID id,
        UUID knowledgeBaseId,
        String title,
        String status
) {
}