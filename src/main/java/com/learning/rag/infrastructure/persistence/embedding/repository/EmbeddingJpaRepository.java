package com.learning.rag.infrastructure.persistence.embedding.repository;

import com.learning.rag.infrastructure.persistence.embedding.entity.EmbeddingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmbeddingJpaRepository
        extends JpaRepository<EmbeddingEntity, UUID> {

    Optional<EmbeddingEntity> findByDocumentChunkId(
            UUID documentChunkId);
}