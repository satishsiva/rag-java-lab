package com.learning.rag.infrastructure.retrieval.sql;

public final class RetrievalSql {

    private RetrievalSql() {
    }

    public static final String FIND_NEAREST = """
            SELECT
                dc.id,
                dc.text,
                1 - (e.vector <=> CAST(? AS vector)) AS similarity
            FROM embedding e
            JOIN document_chunk dc
              ON dc.id = e.document_chunk_id
            ORDER BY e.vector <=> CAST(? AS vector)
            LIMIT ?
            """;
}