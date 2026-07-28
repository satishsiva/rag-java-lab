package com.learning.rag.infrastructure.retrieval.dto;


import java.util.UUID;

public record SearchResultRow(

        UUID chunkId,

        String text,

        double similarity

) {
}