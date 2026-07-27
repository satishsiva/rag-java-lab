package com.learning.rag.domain.embedding;

import java.util.Optional;
import java.util.UUID;

public interface EmbeddingRepository {

    void save(Embedding embedding);

    Optional<Embedding> findByDocumentChunkId(
            UUID documentChunkId);
}