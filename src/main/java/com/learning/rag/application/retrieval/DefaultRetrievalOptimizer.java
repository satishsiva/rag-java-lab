package com.learning.rag.application.retrieval;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DefaultRetrievalOptimizer
        implements RetrievalOptimizer {

    @Override
    public List<ContextBlock> optimize(
            List<SearchResult> searchResults) {

        Map<UUID, SearchGroup> groups = new LinkedHashMap<>();

        AtomicInteger order = new AtomicInteger();
        for (SearchResult result : searchResults) {



            SearchGroup group =
                    groups.computeIfAbsent(

                            result.documentVersionId(),

                            id -> new SearchGroup(
                                    order.getAndIncrement()
                            )
                    );

            group.chunks.add(result);
        }

        List<SearchGroup> orderedGroups =
                new ArrayList<>(groups.values());

        orderedGroups.sort(
                Comparator.comparingInt(g -> g.order)
        );

        List<ContextBlock> contextBlocks =
                new ArrayList<>();

        for (SearchGroup group : orderedGroups) {

            contextBlocks.addAll(
                    mergeGroup(group)
            );
        }

        return contextBlocks;
    }

    private boolean isAdjacent(
            ContextBlockBuilder current,
            SearchResult next) {

        return next.chunkNumber()
                == current.lastChunk + 1;
    }

    private List<ContextBlock> mergeGroup(
            SearchGroup group) {

        group.chunks.sort(
                Comparator.comparingInt(SearchResult::chunkNumber)
        );

        List<ContextBlock> blocks =
                new ArrayList<>();

        ContextBlockBuilder current = null;

        for (SearchResult result : group.chunks) {

            if (current == null) {

                current = new ContextBlockBuilder(result);
                continue;
            }

            if (isAdjacent(current, result)) {

                current.append(result);

            } else {

                blocks.add(current.build());

                current = new ContextBlockBuilder(result);
            }
        }

        if (current != null) {
            blocks.add(current.build());
        }

        return blocks;
    }

    private static class ContextBlockBuilder {

        private final UUID documentVersionId;

        private final int firstChunk;

        private int lastChunk;

        private final StringBuilder text;

        private final double similarity;

        ContextBlockBuilder(SearchResult result) {

            this.documentVersionId = result.documentVersionId();
            this.firstChunk = result.chunkNumber();
            this.lastChunk = result.chunkNumber();
            this.similarity = result.similarity();

            this.text = new StringBuilder(result.text());
        }

        void append(SearchResult result) {

            lastChunk = result.chunkNumber();

            text.append("\n\n")
                    .append(result.text());
        }

        ContextBlock build() {

            return new ContextBlock(

                    documentVersionId,

                    firstChunk,

                    lastChunk,

                    text.toString(),

                    similarity
            );
        }
    }
    private static class SearchGroup {

        private final int order;

        private final List<SearchResult> chunks =
                new ArrayList<>();

        SearchGroup(int order) {
            this.order = order;
        }
    }
}