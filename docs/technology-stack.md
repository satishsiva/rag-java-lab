# RAG Java Lab - Technology Stack

## Overview

RAG Java Lab is intentionally built using widely adopted, production-ready technologies while avoiding frameworks that hide important implementation details.

The goal is to understand every layer involved in a Retrieval-Augmented Generation (RAG) system—from document ingestion to semantic search and, eventually, AI-powered conversations.

---

# Technology Stack Overview

| Layer                 | Technology        |
| --------------------- | ----------------- |
| Language              | Java 21           |
| Framework             | Spring Boot 3     |
| Build Tool            | Maven             |
| Database              | PostgreSQL 17     |
| Vector Database       | pgvector          |
| ORM                   | Spring Data JPA   |
| SQL Access            | Spring JDBC       |
| Database Migration    | Flyway            |
| AI Runtime            | Ollama            |
| Embedding Model       | nomic-embed-text  |
| PDF Processing        | Apache PDFBox     |
| File Storage          | Local File System |
| REST API              | Spring Web        |
| Dependency Injection  | Spring Framework  |
| Boilerplate Reduction | Lombok            |

---

# Java 21

## Purpose

Primary programming language.

### Why Java?

* Mature ecosystem
* Excellent tooling
* Strong type safety
* High performance
* Enterprise adoption
* Long-term support (LTS)

### Why Java 21?

* Latest LTS release
* Modern language features
* Better performance
* Improved garbage collection
* Virtual Threads available for future enhancements

---

# Spring Boot 3

## Purpose

Application framework.

### Responsibilities

* Dependency Injection
* REST APIs
* Configuration management
* Transaction management
* Bean lifecycle
* Integration with JPA and JDBC

### Why Spring Boot?

Spring Boot allows the project to focus on business logic while providing production-ready infrastructure.

Business logic remains independent of the framework through Clean Architecture.

---

# Maven

## Purpose

Build and dependency management.

Responsibilities:

* Dependency resolution
* Compilation
* Packaging
* Testing
* Plugin management

---

# PostgreSQL

## Purpose

Primary relational database.

Stores:

* Knowledge Bases
* Documents
* Document Versions
* Document Chunks
* Embeddings
* Metadata

### Why PostgreSQL?

* Open source
* Reliable
* ACID compliant
* Excellent JSON support
* Rich indexing
* Strong ecosystem

---

# pgvector

## Purpose

Store vector embeddings inside PostgreSQL.

Provides:

* Vector data type
* Similarity search
* Nearest neighbour search

### Why pgvector?

Instead of maintaining two databases:

```text id="d3y1tp"
PostgreSQL

+

Vector Database
```

we can use:

```text id="iuh2mr"
PostgreSQL

+

pgvector
```

This keeps the architecture simpler while still supporting semantic search.

---

# Spring Data JPA

## Purpose

Object-relational mapping.

Used for:

* Simple CRUD
* Entity persistence
* Standard repository operations

### Why JPA?

Most business entities require straightforward persistence.

JPA reduces boilerplate while keeping the domain model clean.

---

# Spring JDBC

## Purpose

Direct SQL execution.

Used for:

* Custom queries
* Performance-sensitive operations
* Complex SQL
* Database-specific features

### Why both JPA and JDBC?

The project intentionally uses both.

| JPA              | JDBC                |
| ---------------- | ------------------- |
| Simple CRUD      | Complex SQL         |
| Less code        | More control        |
| Easy maintenance | Better optimization |

This hybrid approach combines productivity with flexibility.

---

# Flyway

## Purpose

Database version control.

Responsibilities:

* Schema evolution
* Version tracking
* Automatic migrations

Example:

```text id="mrg2nk"
V1

↓

V2

↓

V3

↓

V9
```

Every database change is reproducible.

---

# Ollama

## Purpose

Local AI runtime.

Current responsibilities:

* Generate embeddings

Future responsibilities:

* Local chat models
* Summarization
* Classification

### Why Ollama?

* Runs locally
* No API cost
* No internet required
* Easy experimentation
* Supports many open-source models

---

# nomic-embed-text

## Purpose

Embedding model.

Current characteristics:

* 768-dimensional vectors
* Optimized for semantic search
* Lightweight enough for local development

Current usage:

```text id="9npr7y"
Chunk Text

↓

Embedding

↓

768-dimensional vector
```

---

# Apache PDFBox

## Purpose

Extract text from PDF documents.

Current pipeline:

```text id="l4o4aq"
PDF

↓

Plain Text
```

### Why PDFBox?

* Apache licensed
* Mature
* Reliable
* Pure Java
* Easy integration

---

# Local File Storage

## Purpose

Store uploaded documents.

Current implementation:

```text id="9bzcl7"
Application

↓

Local Storage
```

Future implementations may include:

* Amazon S3
* Azure Blob Storage
* Google Cloud Storage
* MinIO

The application depends only on the `FileStorageService` abstraction.

---

# Spring Web

## Purpose

Expose REST APIs.

Responsibilities:

* HTTP endpoints
* JSON serialization
* Request validation
* Response generation

The API layer contains no business logic.

---

# Lombok

## Purpose

Reduce boilerplate.

Used for:

* Getters
* Setters
* Constructors
* Builders (future)

Improves readability without affecting business logic.

---

# Clean Architecture

The project follows Clean Architecture principles.

```text id="jlwmc9"
API

↓

Application

↓

Domain

↑

Infrastructure
```

Dependencies always point inward.

The Domain layer never depends on:

* Spring
* Database
* AI provider
* File system

---

# AI Abstraction

Current implementation:

```text id="jlwmg1"
EmbeddingGenerator

↓

OllamaEmbeddingGenerator
```

Future implementations:

```text id="r5g3w0"
EmbeddingGenerator

├── OllamaEmbeddingGenerator

├── OpenAIEmbeddingGenerator

├── GeminiEmbeddingGenerator

└── AzureEmbeddingGenerator
```

No business logic changes are required when switching providers.

---

# Current Development Environment

| Component        | Version                   |
| ---------------- | ------------------------- |
| Java             | 21                        |
| Spring Boot      | 3.x                       |
| PostgreSQL       | 17                        |
| pgvector         | Latest compatible version |
| Ollama           | Local Runtime             |
| nomic-embed-text | Latest                    |
| Maven            | 3.x                       |

---

# Future Technologies

The architecture is intentionally prepared for future additions.

Potential technologies include:

### AI

* OpenAI
* Google Gemini
* Azure OpenAI

### Search

* Hybrid Search
* BM25
* Rerankers

### Storage

* Amazon S3
* Azure Blob Storage

### Deployment

* Docker
* Docker Compose
* Kubernetes

### Observability

* Micrometer
* Prometheus
* Grafana

### Security

* Spring Security
* JWT
* OAuth2

### Messaging

* Kafka
* RabbitMQ

---

# Design Philosophy

Technology choices in this project follow a few guiding principles:

* Prefer understanding over convenience.
* Keep business logic independent of frameworks.
* Use abstractions around external services.
* Start simple, then evolve incrementally.
* Choose production-ready technologies where possible.
* Make each component replaceable without impacting the core domain.

The objective is not only to build a working RAG application, but also to understand the reasoning behind every technology involved in the system.
