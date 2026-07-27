package com.learning.rag.infrastructure.persistence.documentversion.adapter;

import com.learning.rag.domain.documentversion.DocumentVersion;
import com.learning.rag.domain.documentversion.DocumentVersionRepository;
import com.learning.rag.infrastructure.persistence.documentversion.entity.DocumentVersionEntity;
import com.learning.rag.infrastructure.persistence.documentversion.mapper.DocumentVersionMapper;
import com.learning.rag.infrastructure.persistence.documentversion.repository.DocumentVersionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DocumentVersionRepositoryJpaAdapter
        implements DocumentVersionRepository {

    private final DocumentVersionJpaRepository jpaRepository;
    private final DocumentVersionMapper mapper;

    public DocumentVersionRepositoryJpaAdapter(
            DocumentVersionJpaRepository jpaRepository,
            DocumentVersionMapper mapper) {

        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(DocumentVersion documentVersion) {

        DocumentVersionEntity entity =
                mapper.toEntity(documentVersion);

        jpaRepository.save(entity);
    }

    @Override
    public Optional<DocumentVersion> findById(UUID id) {

        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<DocumentVersion> findLatestVersion(UUID documentId) {

        return jpaRepository
                .findTopByDocumentIdOrderByVersionNumberDesc(documentId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<DocumentVersion> findCurrentVersion(UUID documentId) {

        return jpaRepository
                .findByDocumentIdAndCurrentTrue(documentId)
                .map(mapper::toDomain);
    }

    @Override
    public void deactivateCurrentVersion(UUID documentId) {

        throw new UnsupportedOperationException(
                "Not implemented yet.");
    }
}