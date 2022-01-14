package com.example.spqr.utils;

import io.leangen.graphql.annotations.GraphQLInputField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortOptionInputField {

    @GraphQLInputField
    private SortOrder sortOrder;
    @GraphQLInputField
    private String sortBy;
}
