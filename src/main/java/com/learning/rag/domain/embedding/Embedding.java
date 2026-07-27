package com.learning.rag.domain.embedding;

import java.time.LocalDateTime;
import java.util.UUID;

public class Embedding {

    private final UUID id;

    private final UUID documentChunkId;

    private final float[] vector;

    private final LocalDateTime createdAt;

    public Embedding(
            UUID id,
            UUID documentChunkId,
            float[] vector,
            LocalDateTime createdAt) {

        this.id = id;
        this.documentChunkId = documentChunkId;
        this.vector = vector;
        this.createdAt = createdAt;
    }

    public static Embedding create(
            UUID documentChunkId,
            float[] vector) {

        return new Embedding(
                UUID.randomUUID(),
                documentChunkId,
                vector,
                LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentChunkId() {
        return documentChunkId;
    }

    public float[] getVector() {
        return vector;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}