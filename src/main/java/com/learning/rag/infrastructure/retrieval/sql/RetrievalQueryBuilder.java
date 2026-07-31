package com.learning.rag.infrastructure.retrieval.sql;

import com.learning.rag.application.retrieval.RetrievalRequest;
import com.learning.rag.application.retrieval.SearchFilter;
import com.learning.rag.infrastructure.retrieval.util.PgVectorConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RetrievalQueryBuilder {

    public RetrievalQuery build(
            RetrievalRequest request,
            float[] queryVector) {

        StringBuilder sql = new StringBuilder( );
        List<Object> parameters = new ArrayList<>();
        SearchFilter filter = request.filter();
        // Parameter for similarity calculation
        parameters.add(PgVectorConverter.toPgVector(queryVector));

        appendBaseQuery( sql);
        appendSearchScope(
                sql,
                filter
        );
        appendKnowledgeBaseFilter(
                sql,
                parameters,
                filter
        );
        appendDocumentFilter(
                sql,
                parameters,
                filter
        );
        appendVersionFilter(
                sql,
                parameters,
                filter
        );
        appendOrdering(
                sql,
                parameters,
                queryVector
        );
        appendLimit(
                sql,
                parameters,
                request
        );

        return new RetrievalQuery(
                sql.toString(),
                parameters.toArray()
        );
    }
    private void appendSearchScope(

            StringBuilder sql,

            SearchFilter filter

    ) {

        if (filter.searchScope()
                == com.learning.rag.application.retrieval.SearchScope.ACTIVE_ONLY) {

            sql.append("""
                
                AND dv.current_version = TRUE
                """);
        }

    }

    private void appendKnowledgeBaseFilter(
            StringBuilder sql,
            List<Object> parameters,
            SearchFilter filter
    ){
        if (filter.knowledgeBase() != null) {

            sql.append("""
        AND d.knowledge_base_id = ?
        """);

            parameters.add(filter.knowledgeBase());
        }
    }
    private void appendDocumentFilter(
            StringBuilder sql,
            List<Object> parameters,
            SearchFilter filter
    ) {

        if (filter.documentId() != null) {

            sql.append("""
                AND d.id = ?
                """);

            parameters.add(filter.documentId());
        }
    }
    private void appendVersionFilter(
            StringBuilder sql,
            List<Object> parameters,
            SearchFilter filter
    ) {

        if (filter.versionNumber() != null) {

            sql.append("""
                AND dv.version_number = ?
                """);

            parameters.add(filter.versionNumber());
        }
    }

    private void appendBaseQuery( StringBuilder sql ){
        sql.append("""
                SELECT
                    dc.id,
                    dc.text,
                    dc.document_version_id,
                    dc.chunk_number,
                    1 - (e.vector <=> CAST(? AS vector)) AS similarity
                FROM embedding e
                JOIN document_chunk dc
                    ON dc.id = e.document_chunk_id
                JOIN document_version dv
                    ON dv.id = dc.document_version_id
                JOIN document d
                    ON d.id = dv.document_id
                WHERE 1 = 1
                """);
    }
    private void appendOrdering(
            StringBuilder sql,
            List<Object> parameters,
            float[] queryVector
    ) {

        sql.append("""
        ORDER BY e.vector <=> CAST(? AS vector)
        """);

        parameters.add(
                PgVectorConverter.toPgVector(queryVector)
        );
    }
    private void appendLimit(
            StringBuilder sql,
            List<Object> parameters,
            RetrievalRequest request
    ) {

        sql.append("""
        LIMIT ?
        """);

        parameters.add(request.topK());
    }
}