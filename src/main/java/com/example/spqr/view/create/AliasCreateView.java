package com.example.spqr.view.create;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.example.spqr.model.Alias;
import com.example.spqr.view.get.AliasView;

@CreatableEntityView
@EntityView(Alias.class)
public interface AliasCreateView extends AliasView {

    void setAlias(String alias);
}
