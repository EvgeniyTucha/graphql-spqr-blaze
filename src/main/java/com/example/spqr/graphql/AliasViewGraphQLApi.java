package com.example.spqr.graphql;

import com.example.spqr.service.AliasViewService;
import com.example.spqr.utils.FilterData;
import com.example.spqr.utils.SortOptionInputField;
import com.example.spqr.view.create.AliasCreateView;
import com.example.spqr.view.get.AliasView;
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
public class AliasViewGraphQLApi {

    private final AliasViewService aliasViewService;

    public AliasViewGraphQLApi(AliasViewService aliasViewService) {
        this.aliasViewService = aliasViewService;
    }

    @GraphQLQuery
    public AliasView getAliasById(@GraphQLArgument(name = "id") Long id,
            @GraphQLEnvironment ResolutionEnvironment env) {
        return aliasViewService.findById(id, env);
    }

    @GraphQLQuery
    public List<AliasView> getAliases(@GraphQLEnvironment ResolutionEnvironment env,
            @GraphQLArgument(name = "firstResult") Integer firstResult,
            @GraphQLArgument(name = "maxResults") Integer maxResults,
            @GraphQLArgument(name = "expression") String expression,
            @GraphQLArgument(name = "sortOptions") List<SortOptionInputField> sortOptions,
            @GraphQLArgument(name = "filters") List<FilterData> filters) {
        return aliasViewService.findAll(env);
    }

    @GraphQLMutation
    public AliasView createAlias(@GraphQLArgument(name = "data") AliasCreateView alias) {
        return aliasViewService.create(alias);
    }
}
