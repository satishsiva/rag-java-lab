package com.learning.rag.infrastructure.ai.ollama.dto;

public record OllamaChatRequest(

        String model,

        String prompt,

        boolean stream

) {
}