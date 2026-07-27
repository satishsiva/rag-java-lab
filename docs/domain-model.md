# RAG Java Lab - Domain Model

## Overview

The domain model represents the core business concepts of the RAG system.

The system organizes information into **Knowledge Bases**, which contain **Documents**. Each document may have multiple **Versions**, each version is split into **Chunks**, and every chunk has an associated **Vector Embedding** used for semantic search.

The domain is designed to support:

* Multiple knowledge bases
* Document versioning
* Safe re-processing
* Semantic search
* Future support for multiple embedding providers

---

# Aggregate Relationship

```text
KnowledgeBase
    │
    ├── Document
            │
            ├── DocumentVersion
                    │
                    ├── DocumentChunk
                            │
                            └── Embedding
```

Each aggregate has a single responsibility.

---

# KnowledgeBase

## Purpose

Represents a logical collection of documents.

Examples:

* HR Policies
* Engineering Documentation
* Product Manuals
* Company Wiki

A Knowledge Base controls how its documents are processed and searched.

---

## Responsibilities

* Store business identity
* Own documents
* Define processing configuration
* Define search configuration
* Maintain lifecycle state

---

## Important Fields

| Field                   | Description             |
| ----------------------- | ----------------------- |
| id                      | Unique identifier       |
| name                    | Business name           |
| description             | Description             |
| status                  | Current lifecycle state |
| processingConfiguration | Chunking configuration  |
| searchConfiguration     | Retrieval configuration |

---

## Lifecycle

```text
DRAFT
    │
    ▼
ACTIVE
    │
    ▼
ARCHIVED
```

Archived knowledge bases become read-only.

---

# Document

## Purpose

Represents a logical business document.

Examples:

* Employee Handbook
* Leave Policy
* Spring Boot Guide

A Document is stable over time.

Only its versions change.

---

## Responsibilities

* Business identity
* Belongs to a Knowledge Base
* Owns multiple versions

---

## Important Fields

| Field           | Description           |
| --------------- | --------------------- |
| id              | Document identifier   |
| knowledgeBaseId | Parent knowledge base |
| title           | Business title        |
| description     | Optional description  |
| status          | Document lifecycle    |

---

# DocumentVersion

## Purpose

Represents one uploaded file belonging to a Document.

Whenever a user uploads a new PDF, DOCX, or TXT file, a new DocumentVersion is created.

Old versions are retained for history.

Only one version is active at a time.

---

## Responsibilities

* Track uploaded file
* Store file metadata
* Store processing state
* Maintain version number
* Control activation

---

## Important Fields

| Field            | Description          |
| ---------------- | -------------------- |
| id               | Version identifier   |
| documentId       | Parent document      |
| versionNumber    | Incremental version  |
| originalFileName | Uploaded file name   |
| storagePath      | File location        |
| checksum         | File checksum        |
| contentType      | MIME type            |
| fileSize         | File size            |
| status           | Processing lifecycle |
| current          | Active version       |

---

## Lifecycle

```text
UPLOADED
     │
     ▼
PROCESSING
     │
     ▼
PROCESSED
```

Future states may include:

* FAILED
* DELETED

---

# DocumentChunk

## Purpose

Represents a small piece of text extracted from a document version.

Chunking makes large documents searchable by semantic similarity.

Example:

```text
Document
    ↓

15 Pages

↓

48 Chunks

↓

48 Embeddings
```

---

## Responsibilities

* Store extracted text
* Preserve chunk ordering
* Store metadata
* Track processing status

---

## Important Fields

| Field             | Description      |
| ----------------- | ---------------- |
| id                | Chunk identifier |
| documentVersionId | Parent version   |
| chunkNumber       | Sequential order |
| text              | Chunk text       |
| metadata          | JSON metadata    |
| status            | Chunk lifecycle  |

---

## Metadata

Metadata is stored separately from the chunk text.

Examples:

```json
{
  "page": 12,
  "section": "Leave Policy",
  "heading": "Annual Leave"
}
```

Future metadata may include:

* page number
* paragraph number
* heading
* source filename
* token count
* language

---

# Embedding

## Purpose

Represents the semantic vector generated from a chunk.

Embeddings allow similarity search inside PostgreSQL using pgvector.

---

## Responsibilities

* Store vector
* Reference a chunk
* Support nearest-neighbour search

---

## Important Fields

| Field           | Description          |
| --------------- | -------------------- |
| id              | Embedding identifier |
| documentChunkId | Parent chunk         |
| vector          | Embedding vector     |
| createdAt       | Creation timestamp   |

---

# Domain Flow

```text
KnowledgeBase
        │
        ▼
Document
        │
        ▼
DocumentVersion
        │
        ▼
DocumentChunk
        │
        ▼
Embedding
```

The relationship is strictly hierarchical.

Each lower-level object belongs to exactly one parent.

---

# Business Rules

## Knowledge Base

* Name cannot be empty.
* Archived knowledge bases cannot be modified.

---

## Document

* Must belong to an existing Knowledge Base.

---

## Document Version

* Version numbers are incremental.
* Only one version can be active.
* Previous version is automatically deactivated after successful processing.

---

## Document Chunk

* Chunk numbers must remain ordered.
* Chunks belong to one document version.

---

## Embedding

* One embedding belongs to exactly one chunk.
* Vector dimensions must match the configured embedding model.

---

# Future Domain Extensions

The model is intentionally designed to support future enhancements without major restructuring.

Potential future aggregates include:

```text
Conversation

Message

PromptTemplate

EmbeddingModel

ChatSession

SearchHistory

Feedback

User
```

These can be introduced while preserving the current aggregate relationships.
