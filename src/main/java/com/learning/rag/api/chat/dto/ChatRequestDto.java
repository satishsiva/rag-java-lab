package com.learning.rag.api.chat.dto;


import com.learning.rag.application.retrieval.SearchFilter;

public record ChatRequestDto(

        String question,
        Integer topK,

        SearchFilter filter

) {
}