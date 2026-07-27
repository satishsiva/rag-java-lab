package com.learning.rag.application.documentversion.event;

import java.util.UUID;

public record DocumentVersionCreatedEvent(

        UUID documentVersionId,

        UUID documentId,

        Integer versionNumber

) {
}