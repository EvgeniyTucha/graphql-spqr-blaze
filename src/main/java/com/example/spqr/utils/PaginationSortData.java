package com.example.spqr.utils;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PaginationSortData {

    private int firstResult;
    private int maxResults;
    private List<SortOption> sortOptions;
    private List<FilterData> filters;
    private String expression;
}