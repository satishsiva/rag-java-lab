package com.learning.rag.infrastructure.ai.ollama.dto;

public record OllamaEmbeddingRequest(
        String model,
        String input
) {
}
