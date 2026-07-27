package com.learning.rag.infrastructure.persistence.documentchunk.adapter;

import com.learning.rag.domain.documentchunk.DocumentChunk;
import com.learning.rag.domain.documentchunk.DocumentChunkRepository;
import com.learning.rag.infrastructure.persistence.documentchunk.entity.DocumentChunkEntity;
import com.learning.rag.infrastructure.persistence.documentchunk.mapper.DocumentChunkMapper;
import com.learning.rag.infrastructure.persistence.documentchunk.repository.DocumentChunkJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DocumentChunkRepositoryJpaAdapter
        implements DocumentChunkRepository {

    private final DocumentChunkJpaRepository jpaRepository;
    private final DocumentChunkMapper mapper;

    public DocumentChunkRepositoryJpaAdapter(
            DocumentChunkJpaRepository jpaRepository,
            DocumentChunkMapper mapper) {

        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(DocumentChunk chunk) {

        DocumentChunkEntity entity =
                mapper.toEntity(chunk);

        jpaRepository.save(entity);
    }

    @Override
    public void saveAll(List<DocumentChunk> chunks) {

        List<DocumentChunkEntity> entities =
                chunks.stream()
                        .map(mapper::toEntity)
                        .toList();

        jpaRepository.saveAll(entities);
    }

    @Override
    public Optional<DocumentChunk> findById(UUID id) {

        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<DocumentChunk> findByDocumentVersionId(
            UUID documentVersionId) {

        return jpaRepository
                .findByDocumentVersionIdOrderByChunkNumber(
                        documentVersionId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}