package com.learning.rag.application.retrieval;

import java.util.List;

public interface RetrievalOptimizer {

    List<ContextBlock> optimize(
            List<SearchResult> searchResults);

}