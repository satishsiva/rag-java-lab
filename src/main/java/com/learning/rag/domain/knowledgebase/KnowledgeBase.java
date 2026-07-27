package com.learning.rag.domain.knowledgebase;


import com.learning.rag.common.exception.BusinessException;

import java.util.UUID;

    public class KnowledgeBase {

        private final UUID id;

        private String name;

        private String description;

        private KnowledgeBaseStatus status;

        private ProcessingConfiguration processingConfiguration;

        private SearchConfiguration searchConfiguration;

        private KnowledgeBase(
                UUID id,
                String name,
                String description,
                ProcessingConfiguration processingConfiguration,
                SearchConfiguration searchConfiguration) {

            this.id = id;
            this.name = name;
            this.description = description;
            this.status = KnowledgeBaseStatus.DRAFT;
            this.processingConfiguration=processingConfiguration;
            this.searchConfiguration=searchConfiguration;
        }

        public static KnowledgeBase create(
                String name,
                String description) {

            return new KnowledgeBase(
                    UUID.randomUUID(),
                    validateName(name),
                    description,
                    ProcessingConfiguration.defaultConfiguration(),
                    SearchConfiguration.defaultConfiguration());
        }

        public void rename(String newName) {

            ensureNotArchived();

            String validatedName = validateName(newName);

            if (this.name.equalsIgnoreCase(validatedName)) {
                return;
            }

            this.name = validatedName;
        }

        private static String validateName(String name) {

            if (name == null || name.isBlank()) {
                throw new BusinessException("Knowledge base name cannot be empty.");
            }

            return name.trim();
        }

        private void ensureNotArchived() {

            if (status == KnowledgeBaseStatus.ARCHIVED) {
                throw new BusinessException("Archived knowledge base cannot be modified.");
            }
        }
        public static KnowledgeBase restore(
                UUID id,
                String name,
                String description,
                KnowledgeBaseStatus status,
                ProcessingConfiguration processingConfiguration,
                SearchConfiguration searchConfiguration) {

            KnowledgeBase knowledgeBase =
                    new KnowledgeBase(id, name, description,
                             processingConfiguration,
                             searchConfiguration);

            knowledgeBase.status = status;

            return knowledgeBase;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public KnowledgeBaseStatus getStatus() {
            return status;
        }
        public ProcessingConfiguration getProcessingConfiguration() {
            return processingConfiguration;
        }

        public SearchConfiguration getSearchConfiguration() {
            return searchConfiguration;
        }
}
