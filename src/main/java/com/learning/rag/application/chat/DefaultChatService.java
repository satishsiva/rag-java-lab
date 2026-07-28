package com.learning.rag.application.chat;

import com.learning.rag.application.ai.ChatGenerator;
import com.learning.rag.application.retrieval.RetrievalRequest;
import com.learning.rag.application.retrieval.RetrievalService;
import com.learning.rag.application.retrieval.SearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultChatService
        implements ChatService {

    private final RetrievalService retrievalService;
    private final PromptBuilder promptBuilder;
    private final ChatGenerator chatGenerator;

    public DefaultChatService(
            RetrievalService retrievalService,
            PromptBuilder promptBuilder,
            ChatGenerator chatGenerator) {

        this.retrievalService = retrievalService;
        this.promptBuilder = promptBuilder;
        this.chatGenerator = chatGenerator;
    }

    @Override
    public ChatResponse ask(
            ChatRequest request) {

        List<SearchResult> searchResults =
                retrievalService.retrieve(

                        new RetrievalRequest(
                                request.question(),
                                5
                        ));

        String prompt =
                promptBuilder.build(
                        request.question(),
                        searchResults);

        String answer =
                chatGenerator.generate(prompt);

        return new ChatResponse(
                answer,
                searchResults);
    }
}