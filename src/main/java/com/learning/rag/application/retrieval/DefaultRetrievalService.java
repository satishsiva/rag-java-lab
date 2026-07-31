package com.learning.rag.application.retrieval;

import com.learning.rag.application.processing.embedding.EmbeddingGenerator;
import com.learning.rag.domain.retrieval.VectorSearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultRetrievalService
        implements RetrievalService {
        private final RetrievalOptimizer retrievalOptimizer;
        private final EmbeddingGenerator embeddingGenerator;
        private final VectorSearchRepository vectorSearchRepository;

        public DefaultRetrievalService(
                RetrievalOptimizer retrievalOptimizer,
                EmbeddingGenerator embeddingGenerator,
                VectorSearchRepository vectorSearchRepository) {
            this.retrievalOptimizer = retrievalOptimizer;

            this.embeddingGenerator = embeddingGenerator;
            this.vectorSearchRepository = vectorSearchRepository;
        }

        @Override
        public List<SearchResult> retrieve(
                RetrievalRequest request) {

            float[] queryEmbedding =
                    embeddingGenerator.generate(
                            request.question());


            List<SearchResult> searchResults =
                    vectorSearchRepository.findNearest(
                            queryEmbedding,
                            request.topK(),
                            request.filter());

            return searchResults;

            // TODO v0.3
// Apply metadata filtering before vector search.
            // TODO v0.4
// Planner Agent determines target knowledge base.
            // TODO v0.5
// Support multiple knowledge bases.

            // TODO v0.6
// Execute retrieval in parallel.

            // TODO v0.7
// Add hybrid BM25 + Vector Search.

            // TODO v0.8
// Add Cross Encoder reranking.

            // TODO v1.0
// Planner Agent selects retrievers.
        }
    }
