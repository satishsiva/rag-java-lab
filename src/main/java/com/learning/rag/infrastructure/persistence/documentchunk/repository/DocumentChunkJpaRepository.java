package com.learning.rag.infrastructure.persistence.documentchunk.repository;

import com.learning.rag.infrastructure.persistence.documentchunk.entity.DocumentChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentChunkJpaRepository
        extends JpaRepository<DocumentChunkEntity, UUID> {

    List<DocumentChunkEntity> findByDocumentVersionIdOrderByChunkNumber(
            UUID documentVersionId);
}