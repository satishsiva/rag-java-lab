package com.learning.rag.controller.knowledgebase;

import com.learning.rag.domain.knowledgebase.KnowledgeBase;

import java.util.UUID;

public record CreateKnowledgeBaseResponse(
        UUID id,
        String name,
        String description,
        String status
) {

    public static CreateKnowledgeBaseResponse from(KnowledgeBase knowledgeBase) {

        return new CreateKnowledgeBaseResponse(
                knowledgeBase.getId(),
                knowledgeBase.getName(),
                knowledgeBase.getDescription(),
                knowledgeBase.getStatus().name()
        );
    }
}