package com.learning.rag.application.chat;

public final class PromptTemplate {

    private PromptTemplate() {
    }

    public static final String SYSTEM_PROMPT = """
            You are a helpful AI assistant.

            Answer ONLY using the provided context.

            If the answer cannot be found in the context,
            reply exactly:

            "I don't know based on the provided documents."

            -------------------------
            CONTEXT
            -------------------------

            """;

    public static final String QUESTION_SECTION = """

            -------------------------
            QUESTION
            -------------------------

            """;
}