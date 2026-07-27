package com.learning.rag.application.knowledgebase.usecase;

import com.learning.rag.application.knowledgebase.command.CreateKnowledgeBaseCommand;
import com.learning.rag.application.knowledgebase.result.CreateKnowledgeBaseResult;
import com.learning.rag.common.exception.BusinessException;
import com.learning.rag.domain.knowledgebase.KnowledgeBase;
import com.learning.rag.domain.knowledgebase.KnowledgeBaseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateKnowledgeBaseUseCase {

    private final KnowledgeBaseRepository repository;

    public CreateKnowledgeBaseUseCase(KnowledgeBaseRepository repository) {
        this.repository = repository;
    }

    public CreateKnowledgeBaseResult create(CreateKnowledgeBaseCommand command) {

        if (repository.existsByName(command.name())) {
            throw new BusinessException(
                    "Knowledge base already exists : " + command.name());
        }

        KnowledgeBase knowledgeBase = KnowledgeBase.create(
                command.name(),
                command.description());

        repository.save(knowledgeBase);

        return new CreateKnowledgeBaseResult(
                knowledgeBase.getId(),
                knowledgeBase.getName(),
                knowledgeBase.getDescription(),
                knowledgeBase.getStatus().name()

        );
    }
}