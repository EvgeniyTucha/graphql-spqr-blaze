package com.example.spqr.repository;

import com.blazebit.domain.runtime.model.DomainModel;
import com.blazebit.expression.ExpressionCompiler;
import com.blazebit.expression.ExpressionSerializer;
import com.blazebit.expression.ExpressionService;
import com.blazebit.expression.Predicate;
import com.blazebit.expression.persistence.PersistenceExpressionSerializerContext;
import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.FullQueryBuilder;
import com.blazebit.persistence.PaginatedCriteriaBuilder;
import com.blazebit.persistence.WhereBuilder;
import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.persistence.view.spi.type.EntityViewProxy;
import com.example.spqr.utils.PaginationSortData;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.execution.ResolutionEnvironment;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;

@Transactional
@Service
public class ViewRepository {
    private final EntityManager em;
    private final CriteriaBuilderFactory cbf;
    private final EntityViewManager evm;
    private final DomainModel domainModel;
    private final ExpressionService expressionService;

    private static final String ENTITY_ALIAS = "persistenceAlias";
    private static final String VIEW_ALIAS = "c";

    private final ModelMapper modelMapper;

    public ViewRepository(EntityManager em, CriteriaBuilderFactory cbf, EntityViewManager evm,
            DomainModel domainModel, ExpressionService expressionService) {
        this.em = em;
        this.cbf = cbf;
        this.evm = evm;
        this.domainModel = domainModel;
        this.expressionService = expressionService;
        modelMapper = new ModelMapper();
    }

    public <T> EntityViewSetting<T, CriteriaBuilder<T>> createSetting(
            Class<T> clazz, @GraphQLEnvironment ResolutionEnvironment env) {
        var setting = EntityViewSetting.create(clazz);
        // Why getSelectionSet() returns duplicates?!?
        var list = new HashSet<>(env.dataFetchingEnvironment.getSelectionSet().getFields());
        for (var field : list) {
            if (!field.getSelectionSet().getFields().isEmpty()) {
                continue;
            }
            var fqn = field.getQualifiedName().replaceAll("/", ".");
            setting.fetch(fqn);
        }
        return setting;
    }

    public <T> EntityViewSetting<T, PaginatedCriteriaBuilder<T>> createPaginationSetting(
            Class<T> clazz, @GraphQLEnvironment ResolutionEnvironment env, PaginationSortData paginationData) {
        var setting = EntityViewSetting.create(clazz, paginationData.getFirstResult(), paginationData.getMaxResults());
        if (CollectionUtils.isNotEmpty(paginationData.getFilters())) {
            paginationData.getFilters().forEach(filterData -> setting.addAttributeFilter(filterData.getAttributeName(), filterData.getFilterName(), filterData.getFilterValue()));
        }
        // Why getSelectionSet() returns duplicates?!?
        var list = new HashSet<>(env.dataFetchingEnvironment.getSelectionSet().getFields());
        for (var field : list) {
            if (!field.getSelectionSet().getFields().isEmpty()) {
                continue;
            }
            var fqn = field.getQualifiedName().replaceAll("/", ".");
            setting.fetch(fqn);
        }
        return setting;
    }

    public <T> T findById(EntityViewSetting<T, CriteriaBuilder<T>> setting, Long id) {
        return evm.find(em, setting, id);
    }

    public <T> List<T> findAll(EntityViewSetting<T, ?> setting, PaginationSortData paginationSortData) {
        Class<T> clazz = setting.getEntityViewClass();

        Class entityClass = evm.getMetamodel().managedView(clazz).getEntityClass();
        CriteriaBuilder<T> criteriaBuilder = cbf.create(em, entityClass);
        if (StringUtils.isNotEmpty(paginationSortData.getExpression())) {
            ExpressionCompiler compiler = expressionService.createCompiler();
            ExpressionCompiler.Context exCompilerContext = compiler.createContext(
                    Collections.singletonMap(VIEW_ALIAS, domainModel.getType(clazz.getSimpleName())));
            Predicate predicate = compiler.createPredicate(paginationSortData.getExpression(), exCompilerContext);

            criteriaBuilder = cbf.create(em, entityClass, ENTITY_ALIAS);
            ExpressionSerializer<WhereBuilder> serializer = expressionService.createSerializer(WhereBuilder.class);

            ExpressionSerializer.Context serializerContext = new PersistenceExpressionSerializerContext<>(
                    expressionService, null).withAlias(VIEW_ALIAS, ENTITY_ALIAS);
            serializer.serializeTo(serializerContext, predicate, criteriaBuilder);
        }
//        System.out.println("\n criteriaBuilder.getQueryString():::" + criteriaBuilder.getQueryString());
        final FullQueryBuilder<T, ?> settings = evm.applySetting(setting, criteriaBuilder);
        paginationSortData.getSortOptions().forEach(sortOption -> settings.orderBy(sortOption.getSortBy(), sortOption.isAscending()));
        return settings.getResultList();
    }

    public <T, V> T persist(V v, Class<T> resultClass) {
        evm.save(em, v);
        return evm.convert(v, resultClass, ConvertOption.IGNORE_MISSING_ATTRIBUTES);
    }

    public <T, V> T update(V v, Class<T> resultClass, Long id) {
        var current = evm.find(em, ((EntityViewProxy) v).$$_getEntityViewClass(), id);
        modelMapper.map(v, current);
        return persist(current, resultClass);
    }
}