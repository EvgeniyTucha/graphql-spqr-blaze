package com.example.spqr.service;

import com.example.spqr.view.create.CatCreateView;
import com.example.spqr.view.create.CatUpdateView;
import com.example.spqr.view.get.CatView;
import io.leangen.graphql.execution.ResolutionEnvironment;

import java.util.List;

public interface CatViewService {
    List<CatView> findAll(ResolutionEnvironment env);
    CatView findById(Long id, ResolutionEnvironment env);
    CatView create(CatCreateView cat);
    CatView update(CatUpdateView cat);
}
