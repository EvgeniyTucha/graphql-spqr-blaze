package com.example.spqr.view.create;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.spqr.model.Cat;
import io.leangen.graphql.annotations.GraphQLInputField;
import io.leangen.graphql.annotations.GraphQLQuery;

@CreatableEntityView
@EntityView(Cat.class)
public interface CatCreateView {

    @IdMapping
//    @GraphQLQuery(name = "id", description = "A cat's id")
    Long getId();

    void setId(Long id);

    String getName();

    @GraphQLInputField
    void setName(String name);

    @GraphQLInputField
    void setDescription(String description);

    String getDescription();

}