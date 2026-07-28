# Ollama Setup

## 1. Download Ollama

Download and install Ollama from:

```text id="5e1kxj"
https://ollama.com/download
```

---

## 2. Verify Installation

Open Command Prompt and run:

```bash id="e0cv0y"
ollama --version
```

Example output:

```text id="g2xq1f"
ollama version 0.32.4
```

---

## 3. Download the Embedding Model

Pull the embedding model used in the project:

```bash id="l9g8h7"
ollama pull nomic-embed-text
```

This downloads the model locally (approximately 274 MB).

---

## 4. Verify Downloaded Models

```bash id="6bb4z2"
ollama list
```

Expected output:

```text id="58jl3e"
NAME

nomic-embed-text:latest
```

---

## 5. Verify Ollama Server

Ollama starts a local REST API on:

```text id="gns1kw"
http://localhost:11434
```

Verify the server is running:

```bash id="4lls4e"
curl http://localhost:11434/api/tags
```

Expected response contains:

```json id="mjwbbv"
{
  "models": [
    {
      "name": "nomic-embed-text:latest"
    }
  ]
}
```

---

## 6. Test Embedding Generation

Generate an embedding using the REST API.

```bash id="drg9pi"
curl http://localhost:11434/api/embed ^
-H "Content-Type: application/json" ^
-d "{\"model\":\"nomic-embed-text\",\"input\":\"Hello World\"}"
```

Expected response:

```json id="qbyks0"
{
  "embeddings": [
    [
      -0.0067,
      0.0013,
      ...
    ]
  ]
}
```

The returned vector contains **768 dimensions**, which matches the PostgreSQL `vector(768)` column used in this project.

---

## 7. Spring Boot Configuration

Configure the Ollama base URL in `application.properties`.

```properties id="ljz74w"
ollama.base-url=http://localhost:11434
ollama.embedding.model=nomic-embed-text
```

---

## 8. Current Project Model

Current embedding model:

```text id="dnks2b"
nomic-embed-text
```

Capabilities:

* Local execution
* Offline
* No API key required
* 768-dimensional embeddings
* Optimized for semantic search

---

## Useful Ollama Commands

### List installed models

```bash id="xq1ggw"
ollama list
```

### Pull a model

```bash id="vfc3im"
ollama pull model-name
```

### Remove a model

```bash id="m6ij5o"
ollama rm model-name
```

### Show running models

```bash id="d25dkm"
ollama ps
```

### View model information

```bash id="trtxm8"
ollama show nomic-embed-text
```

ollama pull llama3.2