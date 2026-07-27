CREATE TABLE knowledge_base
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE,
    description     VARCHAR(500),
    status          VARCHAR(20) NOT NULL
);