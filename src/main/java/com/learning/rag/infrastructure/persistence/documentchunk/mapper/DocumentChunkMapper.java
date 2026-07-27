package com.learning.rag.infrastructure.persistence.documentchunk.mapper;

import com.learning.rag.domain.documentchunk.DocumentChunk;
import com.learning.rag.infrastructure.persistence.documentchunk.entity.DocumentChunkEntity;
import org.springframework.stereotype.Component;

@Component
public class DocumentChunkMapper {

    public DocumentChunkEntity toEntity(DocumentChunk domain) {
    return new DocumentChunkEntity(
            domain.getId(),
            domain.getDocumentVersionId(),
            domain.getChunkNumber(),
            domain.getText(),
            domain.getMetadata(),
            domain.getStatus(),
            domain.getCreatedAt(),
            domain.getUpdatedAt()
    );
    }
    public DocumentChunk toDomain(DocumentChunkEntity entity) {
 return new DocumentChunk(
         entity.getId(),
         entity.getDocumentVersionId(),
         entity.getChunkNumber(),
         entity.getText(),
         entity.getMetadata(),
         entity.getStatus(),
         entity.getCreatedAt(),
         entity.getUpdatedAt()

 );
    }
}
