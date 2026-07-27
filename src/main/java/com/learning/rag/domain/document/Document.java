package com.learning.rag.domain.document;

import com.learning.rag.common.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Document {

    private final UUID id;
    private final UUID knowledgeBaseId;
    private final String title;
    private final String originalFileName;
    private final String contentType;
    private final long fileSize;
    private final DocumentStatus status;
    private final LocalDateTime createdAt;

    private Document(
            UUID id,
            UUID knowledgeBaseId,
            String title,
            String originalFileName,
            String contentType,
            long fileSize,
            DocumentStatus status,
            LocalDateTime createdAt) {

        this.id = id;
        this.knowledgeBaseId = knowledgeBaseId;
        this.title = title;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Document create(
            UUID knowledgeBaseId,
            String title,
            String originalFileName,
            String contentType,
            long fileSize) {

        validateKnowledgeBaseId(knowledgeBaseId);
        validateTitle(title);
        validateOriginalFileName(originalFileName);
        validateContentType(contentType);
        validateFileSize(fileSize);

        return new Document(
                UUID.randomUUID(),
                knowledgeBaseId,
                title.trim(),
                originalFileName.trim(),
                contentType,
                fileSize,
                DocumentStatus.UPLOADED,
                LocalDateTime.now()
        );
    }
    public static Document restore(
            UUID id,
            UUID knowledgeBaseId,
            String title,
            String originalFileName,
            String contentType,
            long fileSize,
            DocumentStatus status,
            LocalDateTime createdAt) {

        return new Document(
                id,
                knowledgeBaseId,
                title,
                originalFileName,
                contentType,
                fileSize,
                status,
                createdAt
        );
    }
    private static void validateKnowledgeBaseId(UUID knowledgeBaseId) {

        if (knowledgeBaseId == null) {
            throw new BusinessException("Knowledge Base Id cannot be null.");
        }
    }

    private static void validateTitle(String title) {

        if (title == null || title.isBlank()) {
            throw new BusinessException("Document title cannot be empty.");
        }

        if (title.length() > 200) {
            throw new BusinessException("Document title cannot exceed 200 characters.");
        }
    }

    private static void validateOriginalFileName(String originalFileName) {

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new BusinessException("Original file name cannot be empty.");
        }
    }

    private static void validateContentType(String contentType) {

        if (contentType == null || contentType.isBlank()) {
            throw new BusinessException("Content type cannot be empty.");
        }
    }

    private static void validateFileSize(long fileSize) {

        if (fileSize <= 0) {
            throw new BusinessException("File size must be greater than zero.");
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}