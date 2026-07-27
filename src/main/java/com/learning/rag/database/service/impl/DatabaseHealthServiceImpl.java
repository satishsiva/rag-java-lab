package com.learning.rag.database.service.impl;

import com.learning.rag.database.model.DatabaseInfo;
import com.learning.rag.database.service.DatabaseHealthService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseHealthServiceImpl implements DatabaseHealthService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DatabaseInfo getDatabaseInfo() {

        String version = jdbcTemplate.queryForObject(
                "SELECT version()",
                String.class
        );

        return new DatabaseInfo(version);
    }
}
