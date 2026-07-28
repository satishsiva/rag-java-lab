package com.learning.rag.infrastructure.retrieval.mapper;


import com.learning.rag.application.retrieval.SearchResult;
import com.learning.rag.infrastructure.retrieval.dto.SearchResultRow;
import org.springframework.stereotype.Component;

@Component
public class SearchResultMapper {

    public SearchResult toApplication(
            SearchResultRow row) {

        return new SearchResult(

                row.chunkId(),

                row.text(),

                row.similarity()

        );
    }
}