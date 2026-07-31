package com.learning.rag.application.chat;


import com.learning.rag.application.retrieval.SearchFilter;

public record ChatRequest(

        String question,

        Integer topK,

        SearchFilter filter

) {
}