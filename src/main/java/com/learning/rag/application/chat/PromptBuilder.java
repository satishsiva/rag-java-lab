package com.learning.rag.application.chat;

import com.learning.rag.application.retrieval.ContextBlock;
import com.learning.rag.application.retrieval.SearchResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {

    public String build(
            String question,
            List<ContextBlock> contextBlocks) {

        StringBuilder prompt = new StringBuilder();

        prompt.append(PromptTemplate.SYSTEM_PROMPT);

        for (ContextBlock context : contextBlocks) {

            prompt.append(context.text())
                    .append("\n\n");
        }
// TODO v0.4
// Include document names.
// TODO v0.5
// Include metadata.
// TODO v0.6
// Include conversation history.
// TODO v0.7
// Compress long contexts.
// TODO v1.0
// Dynamic prompt templates selected by Planner Agent.
        prompt.append(PromptTemplate.QUESTION_SECTION)
                .append(question);

        return prompt.toString();
    }
}

