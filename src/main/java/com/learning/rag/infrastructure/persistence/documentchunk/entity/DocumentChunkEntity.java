package com.learning.rag.infrastructure.persistence.documentchunk.entity;

import com.learning.rag.domain.documentchunk.DocumentChunkStatus;
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
@Table(name = "document_chunk")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentChunkEntity {


    @Id
    UUID id;

    @Column(name = "document_version_id", nullable = false)
    UUID documentVersionId;

    @Column(name = "chunk_number", nullable = false)
    int chunkNumber;

    @Column(
            name = "text",
            nullable = false,
            columnDefinition = "TEXT")
    String text;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    DocumentChunkStatus status;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    public DocumentChunkEntity(
            UUID id,
            UUID documentVersionId,
            int chunkNumber,
            String text,
            String metadata,
            DocumentChunkStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.documentVersionId = documentVersionId;
        this.chunkNumber = chunkNumber;
        this.text = text;
        this.metadata=metadata;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
