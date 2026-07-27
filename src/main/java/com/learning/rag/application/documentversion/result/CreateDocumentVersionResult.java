package com.learning.rag.application.documentversion.result;

import com.learning.rag.domain.documentversion.DocumentVersionStatus;

import java.util.UUID;

public record CreateDocumentVersionResult(

        UUID id,

        UUID documentId,

        Integer versionNumber,

        DocumentVersionStatus status

) {
}