package com.learning.rag.infrastructure.retrieval.util;

public final class PgVectorConverter {

    private PgVectorConverter() {
    }

    public static String toPgVector(
            float[] vector) {

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (int i = 0; i < vector.length; i++) {

            if (i > 0) {
                builder.append(",");
            }

            builder.append(vector[i]);
        }

        builder.append("]");

        return builder.toString();
    }
}