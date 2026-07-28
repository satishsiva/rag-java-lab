package com.learning.rag.application.chat;

public interface ChatService {

    ChatResponse ask(
            ChatRequest request);
}