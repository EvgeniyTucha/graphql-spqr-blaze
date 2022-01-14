package com.example.spqr.view;

import com.blazebit.domain.declarative.DomainType;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.spqr.model.Kitten;
import io.leangen.graphql.annotations.GraphQLQuery;

@DomainType
@EntityView(Kitten.class)
public interface KittenView {

    @IdMapping
    @GraphQLQuery(name = "id", description = "A cat's id")
    Long getId();

    String getName();
    String getDescription();
}
