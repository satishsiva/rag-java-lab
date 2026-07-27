package com.learning.rag.application.documentversion.listener;

import com.learning.rag.application.documentversion.event.DocumentVersionCreatedEvent;
import com.learning.rag.application.processing.DocumentProcessingService;
import com.learning.rag.domain.documentversion.DocumentVersion;
import com.learning.rag.domain.documentversion.DocumentVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentVersionCreatedEventListener {

    private final DocumentProcessingService processingService;

    public DocumentVersionCreatedEventListener(
            DocumentProcessingService processingService) {

        this.processingService = processingService;
    }

    @EventListener
    public void handle(DocumentVersionCreatedEvent event) {

        processingService.process(event.documentVersionId());

    }
}
