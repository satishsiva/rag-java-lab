package com.learning.rag.infrastructure.retrieval.sql;

public record RetrievalQuery(

        String sql,

        Object[] parameters

) {
}