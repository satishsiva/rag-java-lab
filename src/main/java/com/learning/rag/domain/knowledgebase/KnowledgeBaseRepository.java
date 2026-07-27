package com.learning.rag.domain.knowledgebase;

import java.util.Optional;
import java.util.UUID;

public interface KnowledgeBaseRepository {

    boolean existsByName(String name);

    void save(KnowledgeBase knowledgeBase);

    Optional<KnowledgeBase> findById(UUID id);
    boolean existsById(UUID id);
}