package com.example.spqr.view.get;

import com.blazebit.domain.declarative.DomainType;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.spqr.model.Alias;

@DomainType
@EntityView(Alias.class)
public interface AliasView {

    @IdMapping
    Long getId();

    @Mapping("alias2")
    String getAlias();
}
