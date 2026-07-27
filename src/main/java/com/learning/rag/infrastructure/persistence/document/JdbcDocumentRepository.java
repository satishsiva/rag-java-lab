package com.learning.rag.infrastructure.persistence.document;

import com.learning.rag.domain.document.Document;
import com.learning.rag.domain.document.DocumentRepository;
import com.learning.rag.domain.document.DocumentStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcDocumentRepository implements DocumentRepository {

    private static final String BASE_SELECT = """
            SELECT
                id,
                knowledge_base_id,
                title,
                original_file_name,
                content_type,
                file_size,
                status,
                created_at
            FROM document
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcDocumentRepository(
            NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Document document) {

        String sql = """
                INSERT INTO document
                (
                    id,
                    knowledge_base_id,
                    title,
                    original_file_name,
                    content_type,
                    file_size,
                    status,
                    created_at
                )
                VALUES
                (
                    :id,
                    :knowledgeBaseId,
                    :title,
                    :originalFileName,
                    :contentType,
                    :fileSize,
                    :status,
                    :createdAt
                )
                """;

        jdbcTemplate.update(sql, mapParameters(document));
    }

    @Override
    public Optional<Document> findById(UUID id) {

        String sql = BASE_SELECT + """
                WHERE id = :id
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        List<Document> documents = jdbcTemplate.query(
                sql,
                parameters,
                this::mapDocument);

        return documents.stream().findFirst();
    }

    @Override
    public boolean existsById(UUID id) {

        String sql = """
            SELECT COUNT(*)
            FROM document
            WHERE id = :id
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        Integer count = jdbcTemplate.queryForObject(
                sql,
                params,
                Integer.class
        );

        return count != null && count > 0;
    }


    @Override
    public List<Document> findByKnowledgeBaseId(UUID knowledgeBaseId) {

        String sql = BASE_SELECT + """
                WHERE knowledge_base_id = :knowledgeBaseId
                ORDER BY created_at
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("knowledgeBaseId", knowledgeBaseId);

        return jdbcTemplate.query(
                sql,
                parameters,
                this::mapDocument);
    }

    @Override
    public boolean existsByKnowledgeBaseIdAndTitle(
            UUID knowledgeBaseId,
            String title) {

        String sql = """
                SELECT COUNT(*)
                FROM document
                WHERE knowledge_base_id = :knowledgeBaseId
                  AND title = :title
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("knowledgeBaseId", knowledgeBaseId)
                .addValue("title", title);

        Long count = jdbcTemplate.queryForObject(
                sql,
                parameters,
                Long.class);

        return count != null && count > 0;
    }

    private MapSqlParameterSource mapParameters(Document document) {

        return new MapSqlParameterSource()
                .addValue("id", document.getId())
                .addValue("knowledgeBaseId", document.getKnowledgeBaseId())
                .addValue("title", document.getTitle())
                .addValue("originalFileName", document.getOriginalFileName())
                .addValue("contentType", document.getContentType())
                .addValue("fileSize", document.getFileSize())
                .addValue("status", document.getStatus().name())
                .addValue("createdAt", document.getCreatedAt());
    }

    private Document mapDocument(
            ResultSet rs,
            int rowNum) throws SQLException {

        return Document.restore(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("knowledge_base_id")),
                rs.getString("title"),
                rs.getString("original_file_name"),
                rs.getString("content_type"),
                rs.getLong("file_size"),
                DocumentStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

}