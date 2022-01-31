package com.example.spqr.view.get;

import com.blazebit.domain.declarative.DomainType;
import com.blazebit.persistence.view.CollectionMapping;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.spqr.model.Cat;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

@DomainType
@EntityView(Cat.class)
public interface CatView {

    @IdMapping
    @GraphQLQuery(name = "id", description = "A cat's id")
    Long getId();

    String getName();

    String getDescription();

    /*
    * With collections that using @Mapping there are 2 cases to do not get duplicates during expression filtering:
    * 1. Use Set instead of List
    * 2. Use @CollectionMapping(forceUnique = true) with List
    * */
    @CollectionMapping(forceUnique = true)
    @Mapping("Kitten[cat.id = VIEW(id)]")
    List<KittenView> getKittens();

    @CollectionMapping(forceUnique = true)
    @Mapping("Alias[cat.id = VIEW(id)]")
    List<AliasView> getAliases();
}
