package com.learning.rag.controller.documentversion;

import com.learning.rag.application.documentversion.result.CreateDocumentVersionResult;

import java.util.UUID;

public record CreateDocumentVersionResponse(

        UUID id,

        UUID documentId,

        Integer versionNumber,

        String status

) {

    public static CreateDocumentVersionResponse from(
            CreateDocumentVersionResult result) {

        return new CreateDocumentVersionResponse(
                result.id(),
                result.documentId(),
                result.versionNumber(),
                result.status().name()
        );
    }
}