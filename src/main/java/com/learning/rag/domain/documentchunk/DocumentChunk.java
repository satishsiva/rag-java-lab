package com.learning.rag.domain.documentchunk;

import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentChunk {

    private final UUID id;

    private final UUID documentVersionId;

    private final int chunkNumber;

    private final String text;

    private final String metadata;

    private final DocumentChunkStatus status;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public DocumentChunk(

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


    public static DocumentChunk create(

            UUID documentVersionId,

            int chunkNumber,

            String text) {

        LocalDateTime now = LocalDateTime.now();

        return new DocumentChunk(

                UUID.randomUUID(),

                documentVersionId,

                chunkNumber,

                text,

                "{}",

                DocumentChunkStatus.CREATED,

                now,

                now
        );
    }
    public DocumentChunk markEmbedded() {

        return new DocumentChunk(

                id,

                documentVersionId,

                chunkNumber,

                text,
                metadata,

                DocumentChunkStatus.EMBEDDED,

                createdAt,

                LocalDateTime.now()
        );
    }
    public UUID getId() {
        return id;
    }

    public UUID getDocumentVersionId() {
        return documentVersionId;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public String getText() {
        return text;
    }
    public String getMetadata() {
        return metadata;
    }

    public DocumentChunkStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}