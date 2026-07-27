package com.learning.rag.application.knowledgebase.result;

import java.util.UUID;

public record CreateKnowledgeBaseResult(
        UUID id,
        String name,
        String description,
        String status
) {
}