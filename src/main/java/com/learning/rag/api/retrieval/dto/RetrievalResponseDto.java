package com.learning.rag.api.retrieval.dto;

import java.util.UUID;

public record RetrievalResponseDto(

        UUID chunkId,

        String text,

        double similarity

) {
}
