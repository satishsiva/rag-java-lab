package com.learning.rag.infrastructure.persistence.embedding.adapter;

import com.learning.rag.domain.embedding.Embedding;
import com.learning.rag.domain.embedding.EmbeddingRepository;
import com.learning.rag.infrastructure.persistence.embedding.mapper.EmbeddingMapper;
import com.learning.rag.infrastructure.persistence.embedding.repository.EmbeddingJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class EmbeddingRepositoryJpaAdapter
        implements EmbeddingRepository {

    private final EmbeddingJpaRepository embeddingJpaRepository;
    private final EmbeddingMapper embeddingMapper;

    public EmbeddingRepositoryJpaAdapter(
            EmbeddingJpaRepository embeddingJpaRepository,
            EmbeddingMapper embeddingMapper) {

        this.embeddingJpaRepository = embeddingJpaRepository;
        this.embeddingMapper = embeddingMapper;
    }

    @Override
    public void save(Embedding embedding) {

        embeddingJpaRepository.save(
                embeddingMapper.toEntity(embedding));
    }

    @Override
    public Optional<Embedding> findByDocumentChunkId(
            UUID documentChunkId) {

        return embeddingJpaRepository
                .findByDocumentChunkId(documentChunkId)
                .map(embeddingMapper::toDomain);
    }
}