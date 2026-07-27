ALTER TABLE document_chunk
    ADD COLUMN metadata JSONB NOT NULL DEFAULT '{}';