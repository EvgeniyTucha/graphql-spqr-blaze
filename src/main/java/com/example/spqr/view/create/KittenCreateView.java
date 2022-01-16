package com.example.spqr.view.create;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.spqr.model.Cat;
import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;

@CreatableEntityView
@EntityView(Cat.class)
public interface KittenCreateView {

    @IdMapping
    @GraphQLQuery(name = "id", description = "A cat's id")
    Long getId();

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

//    CatView getCat();
//    @GraphQLIgnore
//    void setCat(CatView cat);
}
