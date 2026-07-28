package com.learning.rag.application.retrieval;

import java.util.List;

public interface RetrievalService {

    List<SearchResult> retrieve(RetrievalRequest request);

}
