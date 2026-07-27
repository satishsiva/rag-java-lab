
package com.learning.rag.application.processing.embedding;

import com.learning.rag.domain.documentchunk.DocumentChunk;

import java.util.List;

public interface EmbeddingService {

    void generateEmbeddings(
            List<DocumentChunk> chunks);
}