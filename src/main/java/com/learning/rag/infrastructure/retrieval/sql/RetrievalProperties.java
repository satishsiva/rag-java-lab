package com.learning.rag.infrastructure.retrieval.sql;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag.retrieval")
public class RetrievalProperties {

    private double similarityThreshold = 0.70;
    public double getSimilarityThreshold() {
        return similarityThreshold;
    }

    public void setSimilarityThreshold(double similarityThreshold) {
        this.similarityThreshold = similarityThreshold;
    }



    // getter/setter
}