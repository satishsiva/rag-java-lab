package com.learning.rag.infrastructure.persistence.documentversion.mapper;

import com.learning.rag.domain.documentversion.DocumentVersion;
import com.learning.rag.infrastructure.persistence.documentversion.entity.DocumentVersionEntity;
import org.springframework.stereotype.Component;

@Component
public class DocumentVersionMapper {

    public DocumentVersionEntity toEntity(DocumentVersion domain) {

        return new DocumentVersionEntity(
                domain.getId(),
                domain.getDocumentId(),
                domain.getVersionNumber(),
                domain.getOriginalFileName(),
                domain.getStoragePath(),
                domain.getChecksum(),
                domain.getContentType(),
                domain.getFileSize(),
                domain.getStatus(),
                domain.isCurrent(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    public DocumentVersion toDomain(DocumentVersionEntity entity) {

        return DocumentVersion.restore(
                entity.getId(),
                entity.getDocumentId(),
                entity.getVersionNumber(),
                entity.getOriginalFileName(),
                entity.getStoragePath(),
                entity.getChecksum(),
                entity.getContentType(),
                entity.getFileSize(),
                entity.getStatus(),
                entity.isCurrent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}