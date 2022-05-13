package com.example.spqr.service.impl;

import com.blazebit.persistence.PaginatedCriteriaBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.example.spqr.repository.ViewRepository;
import com.example.spqr.service.AliasViewService;
import com.example.spqr.utils.PaginationSortData;
import com.example.spqr.utils.PaginationSortUtility;
import com.example.spqr.view.create.AliasCreateView;
import com.example.spqr.view.get.AliasView;
import io.leangen.graphql.execution.ResolutionEnvironment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasViewServiceImpl implements AliasViewService {

    private final ViewRepository repository;

    public AliasViewServiceImpl(ViewRepository repository) {
        this.repository = repository;
    }

    public AliasView findById(Long id, ResolutionEnvironment env) {
        return repository.findById(repository.createSetting(AliasView.class, env), id);
    }

    public List<AliasView> findAll(ResolutionEnvironment env) {
        PaginationSortData paginationSortData = PaginationSortUtility.generatePaginationData(env);
        final EntityViewSetting<AliasView, PaginatedCriteriaBuilder<AliasView>> setting =
                repository.createPaginationSetting(AliasView.class, env, paginationSortData);
        return repository.findAll(setting, paginationSortData);
    }

    public AliasView create(AliasCreateView alias) {
        return repository.persist(alias, AliasCreateView.class);
    }
}
