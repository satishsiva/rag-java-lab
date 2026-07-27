package com.learning.rag.application.knowledgebase.command;

public record CreateKnowledgeBaseCommand(
        String name,
        String description
) {
}