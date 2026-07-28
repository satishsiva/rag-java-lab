package com.learning.rag.application.retrieval;

public record RetrievalRequest(

        String question,

        int topK

) {
}
