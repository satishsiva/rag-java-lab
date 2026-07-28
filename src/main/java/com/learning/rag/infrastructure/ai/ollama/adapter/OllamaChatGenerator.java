package com.learning.rag.infrastructure.ai.ollama.adapter;

import com.learning.rag.application.ai.ChatGenerator;
import com.learning.rag.infrastructure.ai.ollama.client.OllamaChatClient;
import org.springframework.stereotype.Component;

@Component
public class OllamaChatGenerator
        implements ChatGenerator {

    private final OllamaChatClient chatClient;

    public OllamaChatGenerator(
            OllamaChatClient chatClient) {

        this.chatClient = chatClient;
    }

    @Override
    public String generate(
            String prompt) {

        return chatClient
                .generate(prompt)
                .response();
    }
}