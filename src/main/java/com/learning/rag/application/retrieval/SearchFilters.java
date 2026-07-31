package com.learning.rag.application.retrieval;

public final class SearchFilters {

    private SearchFilters() {
    }

    public static SearchFilter activeOnly() {

        return new SearchFilter(

                null,

                SearchScope.ACTIVE_ONLY,

                null,

                null

        );
    }
}
