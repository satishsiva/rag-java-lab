package com.learning.rag.infrastructure.persistence.documentversion.repository;

import com.learning.rag.infrastructure.persistence.documentversion.entity.DocumentVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentVersionJpaRepository
        extends JpaRepository<DocumentVersionEntity, UUID> {

    Optional<DocumentVersionEntity> findTopByDocumentIdOrderByVersionNumberDesc(
            UUID documentId);

    Optional<DocumentVersionEntity> findByDocumentIdAndCurrentTrue(
            UUID documentId);
}