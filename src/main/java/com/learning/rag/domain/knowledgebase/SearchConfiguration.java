package com.learning.rag.domain.knowledgebase;

public class SearchConfiguration {

    private final int topK;

    public SearchConfiguration(int topK) {
        this.topK = topK;
    }

    public static SearchConfiguration defaultConfiguration() {
        return new SearchConfiguration(5);
    }

    public int getTopK() {
        return topK;
    }
}