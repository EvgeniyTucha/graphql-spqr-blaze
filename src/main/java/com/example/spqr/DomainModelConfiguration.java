package com.example.spqr;

import com.blazebit.domain.declarative.DeclarativeDomain;
import com.blazebit.domain.declarative.DeclarativeDomainConfiguration;
import com.blazebit.domain.declarative.DomainFunctions;
import com.blazebit.domain.declarative.DomainType;
import com.blazebit.domain.runtime.model.DomainModel;
import com.blazebit.expression.declarative.persistence.PersistenceFunction;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class DomainModelConfiguration {

    @Bean
    public DomainModel domainModel(EntityViewManager evm, EntityViewConfiguration config) {
        DeclarativeDomainConfiguration domainConfig = DeclarativeDomain.getDefaultProvider()
                .createDefaultConfiguration()
                .addDomainFunctions(Functions.class)
                .withService(EntityViewManager.class, evm);
        config.getEntityViews().stream()
                .filter(cl -> cl.getAnnotation(DomainType.class) != null)
                .forEach(domainConfig::addDomainType);
        return domainConfig.createDomainModel();
    }

    @DomainFunctions
    public interface Functions {
        @PersistenceFunction(value = "?1 = ?2", predicate = true)
        boolean contains(Collection<Long> collection, Long id);
    }
}