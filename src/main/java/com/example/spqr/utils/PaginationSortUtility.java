package com.example.spqr.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class PaginationSortUtility {

    private static final Integer DEFAULT_FIRST_RESULT = 0;
    private static final Integer DEFAULT_MAX_RESULTS = 20;
    private static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASC;
    private static final String DEFAULT_SORT_BY = "id";
    private static final List<FilterData> DEFAULT_FILTERS = Collections.emptyList();
    private static final List<SortOption> DEFAULT_SORT_OPTIONS = getDefaultSortOptions();

    public static PaginationSortData generatePaginationData(@GraphQLEnvironment ResolutionEnvironment env) {
        return PaginationSortData.builder()
                .maxResults(getParamFromEnvironment(env, "maxResults", DEFAULT_MAX_RESULTS))
                .firstResult(getParamFromEnvironment(env, "firstResult", DEFAULT_FIRST_RESULT))
                .sortOptions(getParamFromEnvironment(env, "sortOptions", DEFAULT_SORT_OPTIONS))
//                .filters(getParamFromEnvironment(env, "filters", DEFAULT_FILTERS))
                .expression(getParamFromEnvironment(env, "expression", StringUtils.EMPTY))
                .build();
    }

    private static <T> T getParamFromEnvironment(ResolutionEnvironment env, String key, T defaultValue) {
        if (defaultValue instanceof List && env.arguments.get(key) != null) {
            var list = new HashSet<>(env.dataFetchingEnvironment.getSelectionSet().getFields());
            List<SortOption> result = new ArrayList<>();
            final ObjectMapper mapper = new ObjectMapper();
            result.addAll(mapper.convertValue(env.arguments.get(key), new TypeReference<List<SortOption>>() {}));

            for (var field : list) {
                if (MapUtils.isNotEmpty(field.getArguments()) && field.getArguments().containsKey(key)) {
                    result.addAll(mapper.convertValue(field.getArguments().get(key), new TypeReference<List<SortOption>>() {}));
                }
            }
            return (T) result;
        } else {
            return env.arguments != null && env.arguments.containsKey(key) ? (T) env.arguments.get(key) : defaultValue;
        }
    }

    private static List<SortOption> getDefaultSortOptions() {
        return List.of(SortOption.builder().sortBy(DEFAULT_SORT_BY).sortOrder(DEFAULT_SORT_ORDER).build());
    }
}