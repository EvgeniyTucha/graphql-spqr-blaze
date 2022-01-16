package com.example.spqr.graphql;

import com.example.spqr.service.CatViewService;
import com.example.spqr.utils.FilterData;
import com.example.spqr.utils.SortOptionInputField;
import com.example.spqr.view.create.CatCreateView;
import com.example.spqr.view.get.CatView;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.List;

@GraphQLApi
@Service
public class CatViewGraphQLApi {

    private final CatViewService catViewService;

    public CatViewGraphQLApi(CatViewService catViewService) {
        this.catViewService = catViewService;
    }

    @GraphQLQuery
    public CatView getCatById(@GraphQLArgument(name = "id") Long id,
        @GraphQLEnvironment ResolutionEnvironment env) {
        return catViewService.findById(id, env);
    }

    @GraphQLQuery
    public List<CatView> getAllCats(@GraphQLEnvironment ResolutionEnvironment env,
        @GraphQLArgument(name = "firstResult") Integer firstResult,
        @GraphQLArgument(name = "maxResults") Integer maxResults,
        @GraphQLArgument(name = "expression") String expression,
        @GraphQLArgument(name = "sortOptions") List<SortOptionInputField> sortOptions,
        @GraphQLArgument(name = "filters") List<FilterData> filters) {
        return catViewService.findAll(env);
    }

    @GraphQLMutation
    public CatView createCat(@GraphQLArgument(name = "cat") CatCreateView cat) {
        return catViewService.create(cat);
    }
}
