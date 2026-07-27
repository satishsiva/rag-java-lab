package com.learning.rag.application.processing.embedding;

import org.springframework.stereotype.Service;

//@Service
public class DummyEmbeddingGenerator
        implements EmbeddingGenerator {

    @Override
    public float[] generate(String text) {

        return new float[]{
                0.10f,
                0.25f,
                0.80f,
                0.42f
        };
    }
}