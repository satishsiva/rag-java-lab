package com.learning.rag.api.retrieval;

import com.learning.rag.api.retrieval.dto.RetrievalRequestDto;
import com.learning.rag.api.retrieval.dto.RetrievalResponseDto;
import com.learning.rag.application.retrieval.RetrievalRequest;
import com.learning.rag.application.retrieval.RetrievalService;
import com.learning.rag.application.retrieval.SearchFilters;
import com.learning.rag.application.retrieval.SearchResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retrieval")
public class RetrievalController {

    private final RetrievalService retrievalService;

    public RetrievalController(
            RetrievalService retrievalService) {

        this.retrievalService = retrievalService;
    }

    @PostMapping
    public List<RetrievalResponseDto> retrieve(

            @RequestBody RetrievalRequestDto requestDto) {

        int topK =
                requestDto.topK() != null
                        ? requestDto.topK()
                        : 5;

        RetrievalRequest request =
                new RetrievalRequest(
                        requestDto.question(),
                        topK,
                        SearchFilters.activeOnly());

        List<SearchResult> results =
                retrievalService.retrieve(request);

        return results.stream()

                .map(result ->

                        new RetrievalResponseDto(

                                result.chunkId(),

                                result.text(),

                                result.similarity()

                        ))

                .toList();
    }
}