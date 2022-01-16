package com.example.spqr.view.create;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.MappingCorrelatedSimple;
import com.blazebit.persistence.view.UpdatableMapping;
import com.example.spqr.model.Cat;
import com.example.spqr.model.Kitten;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.lang.reflect.Type;
import java.util.List;
import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.serializer.JsonbDeserializer;

@CreatableEntityView
@EntityView(Cat.class)
//@JsonbTypeDeserializer(CatCreateView.Deserializer.class)
public interface CatCreateView {
    @IdMapping
    Long getId();

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

//    @UpdatableMapping
//    @MappingCorrelatedSimple(correlated = Kitten.class, correlationBasis = "this", correlationExpression = "cat = EMBEDDING_VIEW()")
//    List<KittenCreateView> getCreateKittens();
//    void setCreateKittens(List<KittenCreateView> kittens);
//
//    class Deserializer implements JsonbDeserializer<CatCreateView> {
//        @Override
//        public CatCreateView deserialize(javax.json.stream.JsonParser jsonParser,
//            javax.json.bind.serializer.DeserializationContext deserializationContext, Type type) {
//            return null;
//        }
//    }
}