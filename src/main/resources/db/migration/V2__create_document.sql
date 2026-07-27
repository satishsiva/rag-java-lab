CREATE TABLE document (

                          id UUID PRIMARY KEY,

                          knowledge_base_id UUID NOT NULL,

                          title VARCHAR(200) NOT NULL,

                          original_file_name VARCHAR(500) NOT NULL,

                          content_type VARCHAR(255) NOT NULL,

                          file_size BIGINT NOT NULL,

                          status VARCHAR(50) NOT NULL,

                          created_at TIMESTAMP NOT NULL,

                          CONSTRAINT fk_document_knowledge_base
                              FOREIGN KEY (knowledge_base_id)
                                  REFERENCES knowledge_base(id)
);

CREATE INDEX idx_document_knowledge_base
    ON document (knowledge_base_id);

CREATE INDEX idx_document_kb_title
    ON document (knowledge_base_id, title);