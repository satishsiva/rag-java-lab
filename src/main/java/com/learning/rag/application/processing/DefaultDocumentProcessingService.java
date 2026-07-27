package com.learning.rag.application.processing;


import com.learning.rag.application.processing.chunking.TextChunker;
import com.learning.rag.application.processing.embedding.EmbeddingService;
import com.learning.rag.common.exception.BusinessException;
import com.learning.rag.domain.documentchunk.DocumentChunk;
import com.learning.rag.domain.documentchunk.DocumentChunkRepository;
import com.learning.rag.domain.documentversion.DocumentVersion;
import com.learning.rag.domain.documentversion.DocumentVersionRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.learning.rag.application.processing.extraction.TextExtractionService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DefaultDocumentProcessingService
        implements DocumentProcessingService {

    private final DocumentVersionRepository documentVersionRepository;
    private final TextExtractionService textExtractionService;
    private final TextChunker textChunker;
    private final DocumentChunkRepository documentChunkRepository;
    private final EmbeddingService embeddingService;

    public DefaultDocumentProcessingService(
            DocumentVersionRepository documentVersionRepository,
            TextExtractionService textExtractionService,
            TextChunker textChunker,
            DocumentChunkRepository documentChunkRepository,
            EmbeddingService embeddingService) {

        this.documentVersionRepository = documentVersionRepository;
        this.textExtractionService = textExtractionService;
        this.textChunker = textChunker;
        this.documentChunkRepository = documentChunkRepository;
        this.embeddingService = embeddingService;
    }

    @Override
    public void process(UUID versionId) {

        DocumentVersion version = loadVersion(versionId);

        version = markProcessing(version);

        version = performProcessing(version);

        activateVersion(version);
    }

    private DocumentVersion loadVersion(UUID versionId) {

        return documentVersionRepository
                .findById(versionId)
                .orElseThrow(() ->
                        new BusinessException(
                                "Document version not found."));
    }

    private DocumentVersion markProcessing(
            DocumentVersion version) {

        version = version.markProcessing();

        documentVersionRepository.save(version);

        log.info(
                "Version {} is PROCESSING",
                version.getVersionNumber());

        return version;
    }

    private DocumentVersion performProcessing(
            DocumentVersion version) {

        String extractedText = extractText(version);

        List<String> chunkTexts = createChunks(extractedText);

        List<DocumentChunk> chunks =
                saveChunks(version, chunkTexts);
        embeddingService.generateEmbeddings(chunks);

        version = version.markProcessed();

        documentVersionRepository.save(version);

        log.info(
                "Version {} is now PROCESSED",
                version.getVersionNumber());

        return version;
    }

    private String extractText(
            DocumentVersion version) {

        try (InputStream inputStream =
                     Files.newInputStream(
                             Path.of(version.getStoragePath()))) {

            String text =
                    textExtractionService.extract(
                            version.getContentType(),
                            inputStream);

            log.info(
                    "Extracted {} characters",
                    text.length());

            return text;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to extract text from document.",
                    e);
        }
    }

    private List<String> createChunks(
            String extractedText) {

        List<String> chunks =
                textChunker.chunk(extractedText);

        log.info(
                "{} chunks created",
                chunks.size());

        return chunks;
    }
    private List<DocumentChunk> saveChunks(

            DocumentVersion version,

            List<String> chunkTexts) {

        List<DocumentChunk> chunks = new ArrayList<>();

        int chunkNumber = 1;

        for (String text : chunkTexts) {

            chunks.add(

                    DocumentChunk.create(

                            version.getId(),

                            chunkNumber++,

                            text));
        }

        documentChunkRepository.saveAll(chunks);

        log.info(
                "{} chunks created for version {}",
                chunks.size(),
                version.getVersionNumber());

        return chunks;
    }

    private void activateVersion(
            DocumentVersion version) {

        documentVersionRepository
                .findCurrentVersion(version.getDocumentId())
                .ifPresent(currentVersion -> {

                    DocumentVersion deactivated =
                            currentVersion.deactivate();

                    documentVersionRepository.save(deactivated);

                    log.info(
                            "Version {} DEACTIVATED",
                            currentVersion.getVersionNumber());
                });

        DocumentVersion activated =
                version.activate();

        documentVersionRepository.save(activated);

        log.info(
                "Version {} ACTIVATED",
                activated.getVersionNumber());
    }
}