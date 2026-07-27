package com.learning.rag.controller.documentversion;

import com.learning.rag.application.documentversion.command.CreateDocumentVersionCommand;
import com.learning.rag.application.documentversion.result.CreateDocumentVersionResult;
import com.learning.rag.application.documentversion.usecase.CreateDocumentVersionUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.learning.rag.application.documentversion.command.UploadDocumentVersionCommand;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
@RestController
@RequestMapping("/api/document-versions")
public class DocumentVersionController {

    private final CreateDocumentVersionUseCase createDocumentVersionUseCase;

    public DocumentVersionController(
            CreateDocumentVersionUseCase createDocumentVersionUseCase) {

        this.createDocumentVersionUseCase = createDocumentVersionUseCase;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateDocumentVersionResult> upload(

            @RequestParam UUID documentId,

            @RequestPart MultipartFile file)
            throws IOException {

        UploadDocumentVersionCommand command =
                new UploadDocumentVersionCommand(

                        documentId,

                        file.getOriginalFilename(),

                        file.getContentType(),

                        file.getSize(),

                        file.getInputStream());

        CreateDocumentVersionResult result =
                createDocumentVersionUseCase.create(command);

        return ResponseEntity.ok(result);
    }
}