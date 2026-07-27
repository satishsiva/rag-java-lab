package com.learning.rag.infrastructure.persistence.embedding.entity;

import com.pgvector.PGvector;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "embedding")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmbeddingEntity {

    @Id
    private UUID id;

    @Column(name = "document_chunk_id", nullable = false)
    private UUID documentChunkId;


    @JdbcTypeCode(SqlTypes.VECTOR)
    @Column(columnDefinition = "vector(4)")
    private float[] vector;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public EmbeddingEntity(
            UUID id,
            UUID documentChunkId,
            float[] vector,
            LocalDateTime createdAt) {

        this.id = id;
        this.documentChunkId = documentChunkId;
        this.vector = vector;
        this.createdAt = createdAt;
    }
}