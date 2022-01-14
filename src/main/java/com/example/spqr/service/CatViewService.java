package com.example.spqr.service;

import com.example.spqr.view.CatCreateView;
import com.example.spqr.view.CatView;
import io.leangen.graphql.execution.ResolutionEnvironment;

import java.util.List;

public interface CatViewService {
    List<CatView> findAll(ResolutionEnvironment env);
    CatView findById(Long id, ResolutionEnvironment env);
    CatView create(CatCreateView cat);
}
