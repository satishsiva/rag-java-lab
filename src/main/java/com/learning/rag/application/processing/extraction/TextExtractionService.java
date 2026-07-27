package com.learning.rag.application.processing.extraction;

import java.io.IOException;
import java.io.InputStream;

public interface TextExtractionService {

    String extract(
            String contentType,
            InputStream inputStream)
            throws IOException;
}