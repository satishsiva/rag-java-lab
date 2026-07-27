package com.learning.rag.application.processing.extraction;

import com.learning.rag.application.processing.extractor.TextExtractor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DefaultTextExtractionService
        implements TextExtractionService {

    private final List<TextExtractor> extractors;

    public DefaultTextExtractionService(
            List<TextExtractor> extractors) {

        this.extractors = extractors;
    }

    @Override
    public String extract(
            String contentType,
            InputStream inputStream)
            throws IOException {

        TextExtractor extractor =
                extractors.stream()
                        .filter(e -> e.supports(contentType))
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Unsupported content type: "
                                                + contentType));

        return extractor.extract(inputStream);
    }
}