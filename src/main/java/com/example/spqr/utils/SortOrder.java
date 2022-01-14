package com.example.spqr.utils;

import io.leangen.graphql.annotations.GraphQLEnumValue;

public enum SortOrder {
    @GraphQLEnumValue(name = "ASC") ASC,
    @GraphQLEnumValue(name = "DESC") DESC
}