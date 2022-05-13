package com.example.spqr.service;

import com.example.spqr.view.create.AliasCreateView;
import com.example.spqr.view.get.AliasView;
import io.leangen.graphql.execution.ResolutionEnvironment;

import java.util.List;

public interface AliasViewService {
    List<AliasView> findAll(ResolutionEnvironment env);

    AliasView findById(Long id, ResolutionEnvironment env);

    AliasView create(AliasCreateView cat);
}
