package com.learning.rag.infrastructure.ai.ollama.client;

import com.learning.rag.infrastructure.ai.ollama.config.OllamaProperties;
import com.learning.rag.infrastructure.ai.ollama.dto.OllamaEmbeddingRequest;
import com.learning.rag.infrastructure.ai.ollama.dto.OllamaEmbeddingResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OllamaClient {

    private final RestClient restClient;
    private final OllamaProperties properties;

    public OllamaClient(OllamaProperties properties) {

        this.properties = properties;

        this.restClient = RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }

    public OllamaEmbeddingResponse embed(String text) {

        OllamaEmbeddingRequest request =
                new OllamaEmbeddingRequest(
                        properties.getEmbeddingModel(),
                        text
                );

        return restClient.post()
                .uri("/api/embed")
                .body(request)
                .retrieve()
                .body(OllamaEmbeddingResponse.class);
    }
}
