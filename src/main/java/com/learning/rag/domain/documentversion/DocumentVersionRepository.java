package com.learning.rag.domain.documentversion;

import java.util.Optional;
import java.util.UUID;

public interface DocumentVersionRepository {

    void save(DocumentVersion documentVersion);

    Optional<DocumentVersion> findById(UUID id);

    Optional<DocumentVersion> findLatestVersion(UUID documentId);

    Optional<DocumentVersion> findCurrentVersion(UUID documentId);

    void deactivateCurrentVersion(UUID documentId);
}