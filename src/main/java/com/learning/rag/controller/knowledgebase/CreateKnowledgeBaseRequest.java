package com.learning.rag.controller.knowledgebase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CreateKnowledgeBaseRequest(
        @NotBlank
        @Size(max = 500)
        String name,

        @Size(max = 500)
        String description
) {
}