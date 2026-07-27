package com.learning.rag.application.processing.embedding;

import com.learning.rag.domain.documentchunk.DocumentChunk;
import com.learning.rag.domain.embedding.Embedding;
import com.learning.rag.domain.embedding.EmbeddingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultEmbeddingService
        implements EmbeddingService {

    private final EmbeddingGenerator embeddingGenerator;
    private final EmbeddingRepository embeddingRepository;


    public DefaultEmbeddingService(
            EmbeddingGenerator embeddingGenerator,
            EmbeddingRepository embeddingRepository) {

        this.embeddingGenerator = embeddingGenerator;
        this.embeddingRepository = embeddingRepository;
    }


    @Override
    public void generateEmbeddings(
            List<DocumentChunk> chunks) {


        for (DocumentChunk chunk : chunks) {

            float[] vector =
                    embeddingGenerator.generate(
                            chunk.getText());


            Embedding embedding =
                    Embedding.create(
                            chunk.getId(),
                            vector);


            embeddingRepository.save(
                    embedding);
        }
    }
}