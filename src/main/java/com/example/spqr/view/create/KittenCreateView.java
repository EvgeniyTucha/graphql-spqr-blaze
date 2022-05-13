package com.example.spqr.view.create;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.example.spqr.model.Cat;

@CreatableEntityView
@EntityView(Cat.class)
public interface KittenCreateView extends CatCreateView {
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);
}
