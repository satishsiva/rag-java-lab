package com.learning.rag.infrastructure.persistence.embedding.mapper;

import com.learning.rag.domain.embedding.Embedding;
import com.learning.rag.infrastructure.persistence.embedding.entity.EmbeddingEntity;
import com.pgvector.PGvector;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingMapper {

    public EmbeddingEntity toEntity(Embedding domain) {

        return new EmbeddingEntity(
                domain.getId(),
                domain.getDocumentChunkId(),
                domain.getVector(),
                domain.getCreatedAt()
        );
    }

    public Embedding toDomain(EmbeddingEntity entity) {

        return new Embedding(
                entity.getId(),
                entity.getDocumentChunkId(),
                entity.getVector(),
                entity.getCreatedAt()
        );
    }
}