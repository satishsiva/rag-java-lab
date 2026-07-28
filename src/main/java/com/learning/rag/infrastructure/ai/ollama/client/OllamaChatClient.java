package com.learning.rag.infrastructure.ai.ollama.client;

import com.learning.rag.infrastructure.ai.ollama.config.OllamaProperties;
import com.learning.rag.infrastructure.ai.ollama.dto.OllamaChatRequest;
import com.learning.rag.infrastructure.ai.ollama.dto.OllamaChatResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OllamaChatClient {

    private final RestClient restClient;
    private final OllamaProperties properties;

    public OllamaChatClient(
            OllamaProperties properties) {

        this.properties = properties;

        this.restClient = RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }

    public OllamaChatResponse generate(
            String prompt) {

        OllamaChatRequest request =
                new OllamaChatRequest(

                        properties.getChatModel(),

                        prompt,

                        false

                );

        return restClient.post()
                .uri("/api/generate")
                .body(request)
                .retrieve()
                .body(OllamaChatResponse.class);
    }
}