package com.learning.rag.application.processing.extractor;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class DocxTextExtractor
        implements TextExtractor {

    @Override
    public boolean supports(String contentType) {

        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                .equalsIgnoreCase(contentType);
    }

    @Override
    public String extract(InputStream inputStream)
            throws IOException {

        try (XWPFDocument document =
                     new XWPFDocument(inputStream);
             XWPFWordExtractor extractor =
                     new XWPFWordExtractor(document)) {

            return extractor.getText();
        }
    }
}