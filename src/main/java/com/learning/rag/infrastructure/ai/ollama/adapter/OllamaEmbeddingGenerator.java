package com.learning.rag.infrastructure.ai.ollama.adapter;

import com.learning.rag.application.processing.embedding.EmbeddingGenerator;
import com.learning.rag.infrastructure.ai.ollama.client.OllamaEmbeddingClient;
import com.learning.rag.infrastructure.ai.ollama.dto.OllamaEmbeddingResponse;
import org.springframework.stereotype.Component;

@Component
public class OllamaEmbeddingGenerator implements EmbeddingGenerator {

    private final OllamaEmbeddingClient embeddingClient;

    public OllamaEmbeddingGenerator(OllamaEmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    @Override
    public float[] generate(String text) {

        OllamaEmbeddingResponse response =
                embeddingClient.embed(text);

        return response.embeddings().getFirst();
    }
}