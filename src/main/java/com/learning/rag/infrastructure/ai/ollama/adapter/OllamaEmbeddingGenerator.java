package com.learning.rag.infrastructure.ai.ollama.adapter;

import com.learning.rag.application.processing.embedding.EmbeddingGenerator;
import com.learning.rag.infrastructure.ai.ollama.client.OllamaClient;
import com.learning.rag.infrastructure.ai.ollama.dto.OllamaEmbeddingResponse;
import org.springframework.stereotype.Component;

@Component
public class OllamaEmbeddingGenerator implements EmbeddingGenerator {

    private final OllamaClient ollamaClient;

    public OllamaEmbeddingGenerator(OllamaClient ollamaClient) {
        this.ollamaClient = ollamaClient;
    }

    @Override
    public float[] generate(String text) {

        OllamaEmbeddingResponse response =
                ollamaClient.embed(text);

        return response.embeddings().getFirst();
    }
}