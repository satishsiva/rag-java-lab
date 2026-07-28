package com.learning.rag.infrastructure.retrieval.adapter;

import com.learning.rag.application.retrieval.SearchResult;
import com.learning.rag.domain.retrieval.VectorSearchRepository;
import com.learning.rag.infrastructure.retrieval.dto.SearchResultRow;
import com.learning.rag.infrastructure.retrieval.mapper.SearchResultMapper;
import com.learning.rag.infrastructure.retrieval.sql.RetrievalSql;
import com.learning.rag.infrastructure.retrieval.util.PgVectorConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PgVectorSearchRepository
        implements VectorSearchRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SearchResultMapper mapper;

    public PgVectorSearchRepository(
            JdbcTemplate jdbcTemplate,
            SearchResultMapper mapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<SearchResult> findNearest(
            float[] queryVector,
            int topK) {

        String pgVector =
                PgVectorConverter.toPgVector(queryVector);

        List<SearchResultRow> rows =
                jdbcTemplate.query(

                        RetrievalSql.FIND_NEAREST,

                        (rs, rowNum) ->

                                new SearchResultRow(

                                        rs.getObject("id", UUID.class),

                                        rs.getString("text"),

                                        rs.getDouble("similarity")
                                ),

                        pgVector,
                        pgVector,
                        topK
                );

        return rows.stream()
                .map(mapper::toApplication)
                .toList();
    }
}