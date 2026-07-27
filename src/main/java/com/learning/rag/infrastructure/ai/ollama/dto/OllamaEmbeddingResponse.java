package com.learning.rag.infrastructure.ai.ollama.dto;

import java.util.List;

public record OllamaEmbeddingResponse(
        List<float[]> embeddings
) {
}
