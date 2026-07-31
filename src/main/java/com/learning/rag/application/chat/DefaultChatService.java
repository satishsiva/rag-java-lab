package com.learning.rag.application.chat;

import com.learning.rag.application.ai.ChatGenerator;
import com.learning.rag.application.retrieval.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultChatService
        implements ChatService {

    private final RetrievalService retrievalService;
    private final RetrievalOptimizer retrievalOptimizer;
    private final PromptBuilder promptBuilder;
    private final ChatGenerator chatGenerator;

    public DefaultChatService(
            RetrievalService retrievalService,
            RetrievalOptimizer retrievalOptimizer,
            PromptBuilder promptBuilder,
            ChatGenerator chatGenerator) {

        this.retrievalService = retrievalService;
        this.retrievalOptimizer = retrievalOptimizer;
        this.promptBuilder = promptBuilder;
        this.chatGenerator = chatGenerator;
    }

    @Override
    public ChatResponse ask(
            ChatRequest request) {

        SearchFilter filter =
                java.util.Objects.requireNonNullElseGet(
                        request.filter(),
                        SearchFilters::activeOnly
                );

        int topK =
                java.util.Objects.requireNonNullElse(
                        request.topK(),
                        5
                );

        List<SearchResult> searchResults =
                retrievalService.retrieve(
                        new RetrievalRequest(
                                request.question(),
                                topK,
                                filter
                        )
                );

        List<ContextBlock> contextBlocks =
                retrievalOptimizer.optimize(searchResults);

        if (contextBlocks.isEmpty()) {

            return new ChatResponse(
                    "I couldn't find any relevant information in the knowledge base.",
                    searchResults

            );
        }


        String prompt =
                promptBuilder.build(
                        request.question(),
                        contextBlocks);

        String answer =
                chatGenerator.generate(prompt);

        return new ChatResponse(
                answer,
                searchResults);
    }
}