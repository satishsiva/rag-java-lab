package com.learning.rag.infrastructure.retrieval.adapter;

import com.learning.rag.application.retrieval.RetrievalRequest;
import com.learning.rag.application.retrieval.SearchFilter;
import com.learning.rag.application.retrieval.SearchResult;
import com.learning.rag.domain.retrieval.VectorSearchRepository;
import com.learning.rag.infrastructure.retrieval.dto.SearchResultRow;
import com.learning.rag.infrastructure.retrieval.mapper.SearchResultMapper;
import com.learning.rag.infrastructure.retrieval.sql.RetrievalProperties;
import com.learning.rag.infrastructure.retrieval.sql.RetrievalQuery;
import com.learning.rag.infrastructure.retrieval.sql.RetrievalQueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PgVectorSearchRepository
        implements VectorSearchRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SearchResultMapper mapper;
    private final RetrievalQueryBuilder queryBuilder;
    private final RetrievalProperties properties;

    public PgVectorSearchRepository(
            JdbcTemplate jdbcTemplate,
            SearchResultMapper mapper,
            RetrievalQueryBuilder queryBuilder,
            RetrievalProperties properties) {

        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.queryBuilder= queryBuilder;
        this.properties =properties;
    }

    @Override
    public List<SearchResult> findNearest(
            float[] queryVector,
            int topK,
            SearchFilter filter) {



        RetrievalRequest retrievalRequest =
                new RetrievalRequest(
                        null,
                        topK,
                        filter
                );


        RetrievalQuery retrievalQuery =
                queryBuilder.build(
                        retrievalRequest,
                        queryVector
                );

        List<SearchResultRow> rows =
                jdbcTemplate.query(

                        retrievalQuery.sql(),

                        (rs, rowNum) ->

                                new SearchResultRow(
                                        rs.getObject("id", UUID.class),
                                        rs.getString("text"),
                                        rs.getDouble("similarity"),
                                        rs.getObject("document_version_id", UUID.class),
                                        rs.getInt("chunk_number")

                                ),

                        retrievalQuery.parameters()
                );

        return  rows.stream()
                .map(mapper::toApplication)
                .filter(r -> r.similarity() >= properties.getSimilarityThreshold())
                .toList();


    }
}