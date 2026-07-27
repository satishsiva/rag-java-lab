package com.learning.rag.controller.knowledgebase;

import com.learning.rag.application.knowledgebase.command.CreateKnowledgeBaseCommand;
import com.learning.rag.application.knowledgebase.result.CreateKnowledgeBaseResult;
import com.learning.rag.application.knowledgebase.usecase.CreateKnowledgeBaseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeBaseController {

    private final CreateKnowledgeBaseUseCase createKnowledgeBaseUseCase;

    public KnowledgeBaseController(CreateKnowledgeBaseUseCase useCase) {
        this.createKnowledgeBaseUseCase = useCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateKnowledgeBaseResponse create(
            @RequestBody CreateKnowledgeBaseRequest request) {

        CreateKnowledgeBaseCommand command =
                new CreateKnowledgeBaseCommand(
                        request.name(),
                        request.description());

        CreateKnowledgeBaseResult result =
                createKnowledgeBaseUseCase.create(command);


        return new CreateKnowledgeBaseResponse(
                result.id(),
                result.name(),
                result.description(),
                result.status()
        );
    }
}