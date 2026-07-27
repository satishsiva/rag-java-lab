CREATE TABLE embedding
(
    id UUID PRIMARY KEY,

    document_chunk_id UUID NOT NULL,

    vector vector(4) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_embedding_chunk
        FOREIGN KEY(document_chunk_id)
            REFERENCES document_chunk(id)
);