package com.learning.rag.infrastructure.persistence.knowledgebase;

import com.learning.rag.domain.knowledgebase.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcKnowledgeBaseRepository implements KnowledgeBaseRepository {

    private static final String INSERT_SQL = """
            INSERT INTO knowledge_base
            (
                id,
                name,
                description,
                status,
             chunk_size,
             top_k
            )
            VALUES
            (
                :id,
                :name,
                :description,
                :status,
             :chunkSize,
             :topK
                
            )
            """;
    private static final String EXISTS_BY_NAME_SQL = """
        SELECT EXISTS (
            SELECT 1
            FROM knowledge_base
            WHERE LOWER(name) = LOWER(:name)
        )
        """;
    private static final String FIND_BY_ID_SQL = """
    SELECT
        id,
        name,
        description,
        status,
        chunk_size,
        top_k
    FROM knowledge_base
    WHERE id = :id
    """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public JdbcKnowledgeBaseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(KnowledgeBase knowledgeBase) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("id", knowledgeBase.getId());
        parameters.addValue("name", knowledgeBase.getName());
        parameters.addValue("description", knowledgeBase.getDescription());
        parameters.addValue("status", knowledgeBase.getStatus().name());
        parameters.addValue(
                "chunkSize",
                knowledgeBase
                        .getProcessingConfiguration()
                        .getChunkSize());

        parameters.addValue(
                "topK",
                knowledgeBase
                        .getSearchConfiguration()
                        .getTopK());
        namedParameterJdbcTemplate.update(INSERT_SQL, parameters);
    }
    private KnowledgeBase mapKnowledgeBase(ResultSet rs)
            throws SQLException {

        return KnowledgeBase.restore(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("description"),
                KnowledgeBaseStatus.valueOf(rs.getString("status")),
                        new ProcessingConfiguration(
                                rs.getInt("chunk_size")),

                        new SearchConfiguration(
                                rs.getInt("top_k"))
        );
    }

    @Override
    public Optional<KnowledgeBase> findById(UUID id) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("id", id);

        List<KnowledgeBase> results =
                namedParameterJdbcTemplate.query(
                        FIND_BY_ID_SQL,
                        parameters,
                        (rs, rowNum) -> mapKnowledgeBase(rs));

        return results.stream().findFirst();
    }
    @Override
    public boolean existsByName(String name) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", name);

        Boolean exists = namedParameterJdbcTemplate.queryForObject(
                EXISTS_BY_NAME_SQL,
                parameters,
                Boolean.class);

        return Boolean.TRUE.equals(exists);
    }
    @Override
    public boolean existsById(UUID id) {

        String sql = """
            SELECT EXISTS (
                SELECT 1
                FROM knowledge_base
                WHERE id = :id
            )
            """;


        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        Boolean exists = namedParameterJdbcTemplate.queryForObject(
                sql,
                parameters,
                Boolean.class);

        return Boolean.TRUE.equals(exists);

    }
}