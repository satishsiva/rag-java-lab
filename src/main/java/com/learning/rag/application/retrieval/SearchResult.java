
package com.learning.rag.application.retrieval;

import java.util.UUID;

public record SearchResult(

        UUID chunkId,

        String text,

        double similarity

) {
}