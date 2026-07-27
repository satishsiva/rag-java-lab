package com.learning.rag.application.processing.embedding;

public interface EmbeddingGenerator {

    float[] generate(String text);

}