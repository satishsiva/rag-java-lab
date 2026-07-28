package com.learning.rag.application.chat;

import com.learning.rag.application.retrieval.SearchResult;

import java.util.List;

public record ChatResponse(

        String answer,

        List<SearchResult> sources

) {
}