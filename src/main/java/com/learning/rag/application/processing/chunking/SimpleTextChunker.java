package com.learning.rag.application.processing.chunking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleTextChunker implements TextChunker {


    private final int size;

    public SimpleTextChunker(@Value("${rag.chunk.size}") int chunkSize) {
        this.size = chunkSize;
    }

    @Override
    public List<String> chunk(String text) {


        List<String> chunks = new ArrayList<>();

        for (int start = 0;
             start < text.length();
             start += size) {

            int end = Math.min(
                    start + size,
                    text.length());

            chunks.add(
                    text.substring(start, end));
        }

        return chunks;
    }
}
