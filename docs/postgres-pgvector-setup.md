# PostgreSQL + pgvector Setup

## 1. Pull the pgvector Docker image

Downloads PostgreSQL 17 with the pgvector extension already installed.

```bash
docker pull pgvector/pgvector:pg17
```

---

## 2. Create and start the PostgreSQL container

Creates a PostgreSQL container for the RAG project.

```bash
docker run -d ^
  --name rag-postgres ^
  -e POSTGRES_DB=ragdb ^
  -e POSTGRES_USER=raguser ^
  -e POSTGRES_PASSWORD=your_password ^
  -p 55432:5432 ^
  pgvector/pgvector:pg17
```

### Parameters

* `POSTGRES_DB` → Database name
* `POSTGRES_USER` → Database username
* `POSTGRES_PASSWORD` → Database password
* `55432:5432` → Maps container port to local machine

---

## 3. Verify the container

```bash
docker ps
```

You should see:

```text
rag-postgres
```

---

## 4. Connect to PostgreSQL

```bash
docker exec -it rag-postgres psql -U raguser -d ragdb
```

---

## 5. Verify pgvector

```sql
SELECT extname
FROM pg_extension;
```

Expected output:

```text
vector
```

If not present:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
```

---

## 6. Configure Spring Boot

```properties
spring.datasource.url=jdbc:postgresql://localhost:55432/ragdb
spring.datasource.username=raguser
spring.datasource.password=your_password
```

---

## 7. Verify the application

Start the Spring Boot application.

Successful startup should show:

```text
Database Connected Successfully
PostgreSQL 17.x
```

---

## Useful Docker Commands

### Stop the container

```bash
docker stop rag-postgres
```

### Start the container

```bash
docker start rag-postgres
```

### View logs

```bash
docker logs rag-postgres
```

### Remove the container

```bash
docker rm -f rag-postgres
```

> Removing the container deletes the database unless a Docker volume is used.
