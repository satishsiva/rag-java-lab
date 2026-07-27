package com.learning.rag.application.processing.extractor;

import java.io.IOException;
import java.io.InputStream;

public interface TextExtractor {

    /**
     * Returns true if this extractor can
     * process the supplied content type.
     */
    boolean supports(String contentType);

    /**
     * Extracts plain text from the file.
     */
    String extract(InputStream inputStream)
            throws IOException;
}