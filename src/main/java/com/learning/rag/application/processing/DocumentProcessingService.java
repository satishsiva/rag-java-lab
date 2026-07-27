package com.learning.rag.application.processing;

import java.util.UUID;

public interface DocumentProcessingService {

    void process(UUID documentVersionId);

}