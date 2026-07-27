package com.learning.rag.application.document.usecase;


import com.learning.rag.application.document.command.CreateDocumentCommand;
import com.learning.rag.application.document.result.CreateDocumentResult;
import com.learning.rag.domain.document.Document;
import com.learning.rag.domain.document.DocumentRepository;
import com.learning.rag.common.exception.BusinessException;
import com.learning.rag.domain.knowledgebase.KnowledgeBaseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateDocumentUseCase {

    private final DocumentRepository documentRepository;
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    public CreateDocumentUseCase(
            DocumentRepository documentRepository,
            KnowledgeBaseRepository knowledgeBaseRepository) {

        this.documentRepository = documentRepository;
        this.knowledgeBaseRepository = knowledgeBaseRepository;
    }


    public CreateDocumentResult create(
            CreateDocumentCommand command) {

        if (!knowledgeBaseRepository.existsById(command.knowledgeBaseId())) {
            throw new BusinessException("Knowledge base not found.");
        }
        if (documentRepository
                .existsByKnowledgeBaseIdAndTitle(
                        command.knowledgeBaseId(),
                        command.title())) {

            throw new BusinessException(
                    "Document already exists : "
                            + command.title());
        }


        Document document = Document.create(
                command.knowledgeBaseId(),
                command.title(),
                command.originalFileName(),
                command.contentType(),
                command.fileSize()
        );


        documentRepository.save(document);


        return new CreateDocumentResult(
                document.getId(),
                document.getKnowledgeBaseId(),
                document.getTitle(),
                document.getStatus()
        );
    }
}