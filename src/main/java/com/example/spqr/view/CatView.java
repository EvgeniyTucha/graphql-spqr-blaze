package com.example.spqr.view;

import com.blazebit.domain.declarative.DomainType;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.MappingCorrelatedSimple;
import com.example.spqr.model.Cat;
import com.example.spqr.model.Kitten;
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

    @MappingCorrelatedSimple(
            correlationBasis = "id",
            correlationResult = "this",
            correlated = Kitten.class,
            correlationExpression = "cat.id = EMBEDDING_VIEW(id)",
            fetch = FetchStrategy.JOIN
    )
//    @GraphQLIgnore
//    @Mapping("Cat[id = EMBEDDING_VIEW(cat.id)]")
    List<KittenView> getKittens();

}
