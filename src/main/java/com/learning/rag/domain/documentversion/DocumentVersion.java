package com.learning.rag.domain.documentversion;

import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentVersion {

    private final UUID id;

    private final UUID documentId;

    private final Integer versionNumber;

    private final String originalFileName;

    private final String storagePath;

    private final String checksum;

    private final String contentType;

    private final Long fileSize;

    private  final DocumentVersionStatus status;

    private  final boolean current;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private DocumentVersion(
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

    public static DocumentVersion create(
            UUID documentId,
            Integer versionNumber,
            String originalFileName,
            String storagePath,
            String checksum,
            String contentType,
            Long fileSize) {

        return new DocumentVersion(
                UUID.randomUUID(),
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                DocumentVersionStatus.UPLOADED,
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static DocumentVersion restore(
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

        return new DocumentVersion(
                id,
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                status,
                current,
                createdAt,
                updatedAt
        );
    }
    public DocumentVersion activate() {
        if (status != DocumentVersionStatus.PROCESSED) {
            throw new IllegalStateException(
                    "Only processed versions can be activated.");
        }

        return new DocumentVersion(
                id,
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                DocumentVersionStatus.PROCESSED,
                true,
                createdAt,
                LocalDateTime.now()
        );
    }
    public DocumentVersion deactivate() {

        return new DocumentVersion(
                id,
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                status,
                false,
                createdAt,
                LocalDateTime.now()
        );
    }

    public DocumentVersion withStoragePath(String storagePath) {

        return new DocumentVersion(
                id,
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                status,
                current,
                createdAt,
                LocalDateTime.now()
        );
    }
    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public DocumentVersionStatus getStatus() {
        return status;
    }

    public boolean isCurrent() {
        return current;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public DocumentVersion markProcessing() {

        return new DocumentVersion(
                id,
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                DocumentVersionStatus.PROCESSING,
                current,
                createdAt,
                LocalDateTime.now()
        );
    }

    public DocumentVersion markProcessed() {

        return new DocumentVersion(
                id,
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                DocumentVersionStatus.PROCESSED,
                current,
                createdAt,
                LocalDateTime.now()
        );
    }

    public DocumentVersion markFailed() {

        return new DocumentVersion(
                id,
                documentId,
                versionNumber,
                originalFileName,
                storagePath,
                checksum,
                contentType,
                fileSize,
                DocumentVersionStatus.FAILED,
                current,
                createdAt,
                LocalDateTime.now()
        );
    }

}