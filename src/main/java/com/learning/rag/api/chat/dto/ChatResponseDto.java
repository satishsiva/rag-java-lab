package com.learning.rag.api.chat.dto;

import java.util.List;
import java.util.UUID;

public record ChatResponseDto(

        String answer,

        List<SourceDto> sources

) {

    public record SourceDto(

            UUID chunkId,

            String text,

            double similarity

    ) {
    }
}