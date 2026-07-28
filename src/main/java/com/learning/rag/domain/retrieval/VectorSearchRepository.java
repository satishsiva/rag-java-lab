package com.learning.rag.domain.retrieval;

import com.learning.rag.application.retrieval.SearchResult;

import java.util.List;

public interface VectorSearchRepository {

    List<SearchResult> findNearest(

            float[] queryVector,

            int topK

    );
    // TODO v0.3
// Support metadata filtering.
    // TODO v0.5
// Support searching multiple knowledge bases.
    // TODO v0.6
// Support configurable similarity threshold.

}