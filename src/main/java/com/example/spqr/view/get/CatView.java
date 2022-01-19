package com.example.spqr.view.get;

import com.blazebit.domain.declarative.DomainType;
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

    //    @MappingCorrelatedSimple(
//            correlationBasis = "id",
//            correlationResult = "this",
//            correlated = Kitten.class,
//            correlationExpression = "cat.id = EMBEDDING_VIEW(id)",
//            fetch = FetchStrategy.JOIN
//    )
    @Mapping("Kitten[cat.id = VIEW(id)]")
//    @GraphQLIgnore
//    @Mapping("Cat[id = EMBEDDING_VIEW(cat.id)]")
    List<KittenView> getKittens();

    //    @Mapping("Alias[cat.id = VIEW(id)]")
    List<AliasView> getAliases();
}
