package com.learning.rag.application.retrieval;

import java.util.UUID;

public record SearchFilter(

        String knowledgeBase,

        SearchScope searchScope,

        UUID documentId,

        Integer versionNumber

) {
}