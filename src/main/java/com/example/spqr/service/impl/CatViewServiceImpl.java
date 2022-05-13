package com.example.spqr.service.impl;

import com.blazebit.persistence.PaginatedCriteriaBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.example.spqr.repository.ViewRepository;
import com.example.spqr.service.CatViewService;
import com.example.spqr.utils.PaginationSortData;
import com.example.spqr.utils.PaginationSortUtility;
import com.example.spqr.view.create.CatUpdateView;
import com.example.spqr.view.get.CatView;
import com.example.spqr.view.create.CatCreateView;
import io.leangen.graphql.execution.ResolutionEnvironment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatViewServiceImpl implements CatViewService {

    private final ViewRepository repository;

    public CatViewServiceImpl(ViewRepository repository) {
        this.repository = repository;
    }

    public CatView findById(Long id, ResolutionEnvironment env) {
        return repository.findById(repository.createSetting(CatView.class, env), id);
    }

    public List<CatView> findAll(ResolutionEnvironment env) {
        PaginationSortData paginationSortData = PaginationSortUtility.generatePaginationData(env);
        final EntityViewSetting<CatView, PaginatedCriteriaBuilder<CatView>> setting =
                repository.createPaginationSetting(CatView.class, env, paginationSortData);
        return repository.findAll(setting, paginationSortData);
    }

    public CatView create(CatCreateView cat) {
        return repository.persist(cat, CatView.class);
    }

    public CatView update(CatUpdateView cat) {
        return repository.persist(cat, CatView.class);
    }
}
