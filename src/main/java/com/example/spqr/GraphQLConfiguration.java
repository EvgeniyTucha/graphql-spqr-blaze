package com.example.spqr;

import com.blazebit.persistence.integration.jackson.EntityViewAwareObjectMapper;
import com.blazebit.persistence.view.EntityViewManager;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.DefaultInclusionStrategy;
import io.leangen.graphql.metadata.strategy.InclusionStrategy;
import io.leangen.graphql.metadata.strategy.InputFieldInclusionParams;
import io.leangen.graphql.metadata.strategy.value.ValueMapperFactory;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

@Component
@Configuration
public class GraphQLConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLConfiguration.class);

    @Bean
    public GraphQLSchema getSchema(ValueMapperFactory valueMapperFactory) {
        var applicationContext = ApplicationContextProvider.getApplicationContext();

        var generator = new GraphQLSchemaGenerator()
            .withBasePackages(getClass().getPackageName()) // assumes that we're in root level
            .withValueMapperFactory(valueMapperFactory);

        // scan for API beans
        for (String beanName : applicationContext.getBeanNamesForAnnotation(GraphQLApi.class)) {
            Object bean = applicationContext.getBean(beanName);
            // remove generated suffix from cglib proxy
            int stripTo = bean.getClass().getName().indexOf("$$");
            if (stripTo < 0) {
                stripTo = bean.getClass().getName().length();
            }
            String fqdn = bean.getClass().getName().substring(0, stripTo);
            try {
                generator = generator.withOperationsFromSingleton(bean,
                                                                  Class.forName(fqdn, false, bean.getClass().getClassLoader()));
            } catch (ClassNotFoundException cnfe) {
                logger.warn("Found API in {}, but cannot register {}", fqdn, cnfe.getMessage());
            }
        }
        return generator.generate();
    }

    @Bean
    public GraphQL graphQL(GraphQLSchema schema) {
        return new GraphQL.Builder(schema).build();
    }

}
