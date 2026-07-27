CREATE TABLE document_chunk
(
    id UUID PRIMARY KEY,

    document_version_id UUID NOT NULL,

    chunk_number INTEGER NOT NULL,

    text TEXT NOT NULL,

    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_document_chunk_version
        FOREIGN KEY (document_version_id)
            REFERENCES document_version(id)
);
ALTER TABLE document_chunk
    ADD CONSTRAINT uk_document_chunk_number
        UNIQUE(document_version_id, chunk_number);