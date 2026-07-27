package com.learning.rag.domain.knowledgebase;
public class ProcessingConfiguration {

    private final int chunkSize;

    public ProcessingConfiguration(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public static ProcessingConfiguration defaultConfiguration() {
        return new ProcessingConfiguration(500);
    }

    public int getChunkSize() {
        return chunkSize;
    }
}