package com.learning.rag.infrastructure.persistence.documentversion.entity;

import com.learning.rag.domain.documentversion.DocumentVersionStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document_version")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentVersionEntity {

    @Id
    private UUID id;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "storage_path")
    private String storagePath;

    @Column(name = "checksum")
    private String checksum;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentVersionStatus status;

    @Column(name = "current_version", nullable = false)
    private boolean current;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public DocumentVersionEntity(
            UUID id,
            UUID documentId,
            Integer versionNumber,
            String originalFileName,
            String storagePath,
            String checksum,
            String contentType,
            Long fileSize,
            DocumentVersionStatus status,
            boolean current,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.documentId = documentId;
        this.versionNumber = versionNumber;
        this.originalFileName = originalFileName;
        this.storagePath = storagePath;
        this.checksum = checksum;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.status = status;
        this.current = current;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}