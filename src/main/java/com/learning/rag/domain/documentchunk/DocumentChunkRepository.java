package com.learning.rag.domain.documentchunk;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentChunkRepository {

    void save(DocumentChunk chunk);

    void saveAll(List<DocumentChunk> chunks);

    Optional<DocumentChunk> findById(UUID id);

    List<DocumentChunk> findByDocumentVersionId(
            UUID documentVersionId);
}