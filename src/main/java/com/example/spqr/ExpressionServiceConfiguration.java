package com.example.spqr;

import com.blazebit.domain.runtime.model.DomainModel;
import com.blazebit.expression.ExpressionService;
import com.blazebit.expression.Expressions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpressionServiceConfiguration {

    @Bean
    public ExpressionService expressionService(DomainModel domainModel) {
        return Expressions.forModel(domainModel);
    }
}