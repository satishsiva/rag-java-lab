package com.learning.rag.controller.document;

import com.learning.rag.application.document.command.CreateDocumentCommand;
import com.learning.rag.application.document.result.CreateDocumentResult;
import com.learning.rag.application.document.usecase.CreateDocumentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final CreateDocumentUseCase createDocumentUseCase;

    public DocumentController(
            CreateDocumentUseCase createDocumentUseCase) {

        this.createDocumentUseCase = createDocumentUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateDocumentResponse create(
            @RequestBody CreateDocumentRequest request) {

        CreateDocumentCommand command =
                new CreateDocumentCommand(
                        request.knowledgeBaseId(),
                        request.title(),
                        request.originalFileName(),
                        request.contentType(),
                        request.fileSize()
                );

        CreateDocumentResult result =
                createDocumentUseCase.create(command);

        return new CreateDocumentResponse(
                result.id(),
                result.knowledgeBaseId(),
                result.title(),
                result.status().name()
        );
    }
}