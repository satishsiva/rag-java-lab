package com.learning.rag.domain.document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository {

    void save(Document document);

    Optional<Document> findById(UUID id);

    List<Document> findByKnowledgeBaseId(UUID knowledgeBaseId);

    boolean existsByKnowledgeBaseIdAndTitle(
            UUID knowledgeBaseId,
            String title);
    boolean existsById(UUID id);
}