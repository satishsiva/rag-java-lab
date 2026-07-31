package com.learning.rag.application.retrieval;

import java.util.UUID;

public record ContextBlock(

        UUID documentVersionId,

        int firstChunk,

        int lastChunk,

        String text,

        double similarity

) {
}