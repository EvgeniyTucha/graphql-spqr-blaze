package com.example.spqr.utils;

import io.leangen.graphql.annotations.GraphQLInputField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Builder
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterData implements Serializable {
    @GraphQLInputField
    private String attributeName;
    @GraphQLInputField
    private String filterName = StringUtils.EMPTY;
    @GraphQLInputField
    private String filterValue;
}
