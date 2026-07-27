# RAG Java Lab - Architecture

## Overview

**RAG Java Lab** is a learning project built to understand how Retrieval-Augmented Generation (RAG) systems work from the ground up.

The objective of this project is **not** to build the fastest RAG application, but to understand every layer involved in a production-quality AI system.

Instead of relying on frameworks that hide the implementation details, every major component is implemented explicitly so that the complete document ingestion, processing, retrieval, and chat pipelines are fully understood.

The project follows **Clean Architecture** principles with a clear separation between business logic, application workflows, infrastructure, and API layers.

---

# Architecture Goals

The primary goals of this project are:

* Understand how a modern RAG system works internally.
* Keep business logic independent of infrastructure.
* Allow AI providers to be replaced without changing business logic.
* Support document versioning.
* Support multiple knowledge bases.
* Build an extensible architecture that can later support:

    * OpenAI
    * Ollama
    * Gemini
    * Azure OpenAI
    * Hybrid Search
    * Reranking
    * Conversation Memory

---

# High Level Architecture

```text
                +----------------------+
                |     REST API         |
                |   Controllers        |
                +----------+-----------+
                           |
                           |
                +----------v-----------+
                |    Application       |
                |     Use Cases        |
                |   Domain Services    |
                +----------+-----------+
                           |
          +----------------+----------------+
          |                                 |
          |                                 |
+---------v---------+             +---------v---------+
|      Domain       |             |  Infrastructure   |
| Business Objects  |             | DB / AI / Storage |
| Repository Ports  |             | Repository Adapters|
+-------------------+             +-------------------+
```

The application always depends inward toward the Domain.

Infrastructure implements interfaces defined by the Domain or Application layers.

---

# Package Structure

```text
src/main/java
│
├── common
│
├── domain
│
├── application
│
├── infrastructure
│
└── api
```

---

# Layer Responsibilities

## Domain

The Domain layer contains the business model.

Responsibilities:

* Business entities
* Aggregate roots
* Business rules
* Repository interfaces
* Domain enums
* Domain exceptions

The Domain layer contains **no Spring framework code**, database code, or AI implementation.

Example:

```text
KnowledgeBase
Document
DocumentVersion
DocumentChunk
Embedding
```

---

## Application

The Application layer orchestrates business workflows.

Responsibilities:

* Use Cases
* Commands
* Results
* Events
* Application Services

Example:

```text
CreateKnowledgeBaseUseCase

CreateDocumentVersionUseCase

ProcessDocumentVersionUseCase

EmbeddingService

ChunkingService

TextExtractionService
```

The Application layer knows **what** should happen but not **how** infrastructure performs it.

---

## Infrastructure

Infrastructure contains implementation details.

Responsibilities:

* PostgreSQL
* JPA
* JDBC
* pgvector
* Flyway
* Ollama
* File Storage
* PDF Extraction

Examples:

```text
JdbcKnowledgeBaseRepository

DocumentVersionJpaRepository

OllamaEmbeddingGenerator

LocalFileStorageService

PdfBoxTextExtractor
```

Infrastructure depends on the Domain, never the reverse.

---

## API

The API layer exposes REST endpoints.

Responsibilities:

* Request validation
* DTO mapping
* Calling Use Cases
* Returning responses

No business logic should exist in controllers.

---

# Current Domain Model

```
KnowledgeBase
    │
    ├── Documents
            │
            ├── DocumentVersions
                    │
                    ├── DocumentChunks
                            │
                            └── Embeddings
```

---

# Current Document Processing Pipeline

```
Upload Document
        │
        ▼
Store Original File
        │
        ▼
Create Document Version
        │
        ▼
Extract Text
        │
        ▼
Chunk Text
        │
        ▼
Persist Chunks
        │
        ▼
Generate Embeddings (Ollama)
        │
        ▼
Store Embeddings (pgvector)
        │
        ▼
Activate Latest Version
```

---

# AI Integration

The project currently uses:

* Ollama
* nomic-embed-text

Current embedding size:

```
768 dimensions
```

The AI integration is abstracted behind the `EmbeddingGenerator` interface.

Current implementation:

```
EmbeddingGenerator
        │
        ▼
OllamaEmbeddingGenerator
```

Future implementations may include:

* OpenAIEmbeddingGenerator
* GeminiEmbeddingGenerator
* AzureEmbeddingGenerator

without modifying the Application layer.

---

# Persistence Strategy

The project intentionally uses a hybrid persistence strategy.

## JPA

Used for:

* Simple CRUD
* Entity persistence
* Standard repository operations

## JDBC

Used for:

* Custom SQL
* Performance-sensitive queries
* Queries requiring full SQL control

This approach balances developer productivity with flexibility.

---

# Database Technologies

Current stack:

* PostgreSQL
* pgvector
* Flyway

Document embeddings are stored as PostgreSQL vectors.

---

# Current Project Status

Completed:

* Knowledge Base management
* Document management
* Document versioning
* Local file storage
* PDF text extraction
* Chunk generation
* Chunk persistence
* Metadata persistence
* Ollama integration
* Embedding generation
* Vector storage
* Version activation workflow

---

# Upcoming Architecture

The next phase introduces Retrieval.

```
Question
      │
      ▼
Generate Query Embedding
      │
      ▼
Vector Similarity Search
      │
      ▼
Top K Chunks
```

Later phases will introduce:

```
Prompt Builder
        │
        ▼
LLM
        │
        ▼
Chat Response
```

Future enhancements include:

* Hybrid Search
* Metadata Filtering
* Conversation History
* Streaming Responses
* Multiple AI Providers
* Docker Deployment
* Monitoring
* Caching
* Authentication
* Production Deployment

---

# Guiding Principles

This project follows several architectural principles:

* Business logic must not depend on frameworks.
* Infrastructure is replaceable.
* AI providers are interchangeable.
* Every layer has a single responsibility.
* Explicit implementations are preferred over hidden abstractions for learning purposes.
* Build a production-quality architecture while understanding every component involved.
