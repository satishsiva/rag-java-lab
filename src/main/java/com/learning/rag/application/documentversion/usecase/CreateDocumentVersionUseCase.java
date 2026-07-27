package com.learning.rag.application.documentversion.usecase;

import com.learning.rag.application.documentversion.command.UploadDocumentVersionCommand;
import com.learning.rag.application.documentversion.event.DocumentVersionCreatedEvent;
import com.learning.rag.application.documentversion.result.CreateDocumentVersionResult;
import com.learning.rag.application.storage.FileStorageService;
import com.learning.rag.common.exception.BusinessException;
import com.learning.rag.domain.document.DocumentRepository;
import com.learning.rag.domain.documentversion.DocumentVersion;
import com.learning.rag.domain.documentversion.DocumentVersionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class CreateDocumentVersionUseCase {

    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final FileStorageService fileStorageService;
    private final ApplicationEventPublisher eventPublisher;

    public CreateDocumentVersionUseCase(
            DocumentRepository documentRepository,
            DocumentVersionRepository documentVersionRepository,
            FileStorageService fileStorageService,
            ApplicationEventPublisher eventPublisher) {

        this.documentRepository = documentRepository;
        this.documentVersionRepository = documentVersionRepository;
        this.fileStorageService = fileStorageService;
        this.eventPublisher = eventPublisher;
    }

    public CreateDocumentVersionResult create(
            UploadDocumentVersionCommand command) {

        if (!documentRepository.existsById(command.documentId())) {
            throw new BusinessException("Document not found.");
        }

        int nextVersion = documentVersionRepository
                .findLatestVersion(command.documentId())
                .map(version -> version.getVersionNumber() + 1)
                .orElse(1);

        DocumentVersion documentVersion = DocumentVersion.create(
                command.documentId(),
                nextVersion,
                command.originalFileName(),
                null,   // storagePath
                null,   // checksum
                command.contentType(),
                command.fileSize()
        );

        String storagePath;

        try {

            storagePath = fileStorageService.store(
                    documentVersion.getDocumentId(),
                    documentVersion.getId(),
                    command.originalFileName(),
                    command.inputStream());

        } catch (IOException e) {

            throw new BusinessException(
                    "Unable to store uploaded file.");
        }

        documentVersion =
                documentVersion.withStoragePath(storagePath);

        documentVersionRepository.save(documentVersion);

        eventPublisher.publishEvent(
                new DocumentVersionCreatedEvent(
                        documentVersion.getId(),
                        documentVersion.getDocumentId(),
                        documentVersion.getVersionNumber()
                )
        );

        return new CreateDocumentVersionResult(
                documentVersion.getId(),
                documentVersion.getDocumentId(),
                documentVersion.getVersionNumber(),
                documentVersion.getStatus()
        );
    }
}