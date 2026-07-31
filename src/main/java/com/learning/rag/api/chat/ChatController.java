package com.learning.rag.api.chat;

import com.learning.rag.api.chat.dto.ChatRequestDto;
import com.learning.rag.api.chat.dto.ChatResponseDto;
import com.learning.rag.application.chat.ChatRequest;
import com.learning.rag.application.chat.ChatResponse;
import com.learning.rag.application.chat.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(
            ChatService chatService) {

        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponseDto chat(
            @RequestBody ChatRequestDto requestDto) {

        ChatResponse response =
                chatService.ask(

                        new ChatRequest(

                                requestDto.question(),

                                requestDto.topK(),

                                requestDto.filter()

                        )

                );

        List<ChatResponseDto.SourceDto> sources =

                response.sources()
                        .stream()
                        .map(source ->

                                new ChatResponseDto.SourceDto(

                                        source.chunkId(),

                                        source.text(),

                                        source.similarity()

                                )

                        )
                        .toList();

        return new ChatResponseDto(

                response.answer(),

                sources

        );
    }
}