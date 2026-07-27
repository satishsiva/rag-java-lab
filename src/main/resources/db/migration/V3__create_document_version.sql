-- ==========================================================
-- Table: document_version
-- Stores every uploaded version of a document.
-- Only one version should be marked as current.
-- ==========================================================

CREATE TABLE document_version (

                                  id UUID PRIMARY KEY,

                                  document_id UUID NOT NULL,

                                  version_number INTEGER NOT NULL,

                                  original_file_name VARCHAR(255) NOT NULL,

                                  storage_path VARCHAR(500),

                                  checksum VARCHAR(128),

                                  content_type VARCHAR(100),

                                  file_size BIGINT,

                                  status VARCHAR(30) NOT NULL,

                                  current_version BOOLEAN NOT NULL,

                                  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

                                  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

                                  CONSTRAINT fk_document_version_document
                                      FOREIGN KEY (document_id)
                                          REFERENCES document(id),

                                  CONSTRAINT uk_document_version
                                      UNIQUE (document_id, version_number)
);
-- Lookup indexes

CREATE INDEX idx_document_version_document
    ON document_version(document_id);

CREATE INDEX idx_document_version_current
    ON document_version(document_id, current_version);