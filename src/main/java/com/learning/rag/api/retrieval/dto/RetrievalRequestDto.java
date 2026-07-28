package com.learning.rag.api.retrieval.dto;

public record RetrievalRequestDto(

        String question,

        Integer topK

) {
}