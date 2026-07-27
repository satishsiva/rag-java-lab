package com.learning.rag.application.processing.chunking;

import java.util.List;

public interface TextChunker {

    List<String> chunk(String text);
}