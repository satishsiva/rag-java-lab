package com.learning.rag.application.processing.extractor;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class TxtTextExtractor
        implements TextExtractor {

    @Override
    public boolean supports(String contentType) {

        return "text/plain".equalsIgnoreCase(contentType);
    }

    @Override
    public String extract(
            InputStream inputStream)
            throws IOException {

        return new String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8);
    }
}