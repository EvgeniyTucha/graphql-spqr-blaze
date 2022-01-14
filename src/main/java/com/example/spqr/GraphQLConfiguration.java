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

    private GraphQLSchema schema;
    private GraphQL graphQL;
    private ValueMapperFactory valueMapperFactory;
//    private final ObjectMapper objectMapper;

    public GraphQLConfiguration() {
//        this.objectMapper = objectMapper;
        buildSchema();
    }

    private void buildSchema() {

        // support jackson to instantiate entity views
        valueMapperFactory = JacksonValueMapperFactory.builder().withPrototype(new ObjectMapper()).build();
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
        schema = generator.generate();
        graphQL = new GraphQL.Builder(schema).build();
    }

    @Bean
    public GraphQLSchema getSchema() {
        return schema;
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @Bean
    public ValueMapperFactory valueMapperFactory() {
        return valueMapperFactory;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer blazePersistenceJsonCustomizer(EntityViewManager evm) {
        Module module = new Module() {
            @Override
            public String getModuleName() {
                return "com.blazebit.persistence";
            }

            @Override
            public Version version() {
                return Version.unknownVersion();
            }

            @Override
            public void setupModule(SetupContext context) {
                new EntityViewAwareObjectMapper(evm, context.getOwner(), null);
            }
        };
        return builder -> builder.modules(module);
    }

    @Bean
    @ConditionalOnMissingBean
    public ValueMapperFactory valueMapperFactory(ObjectMapper objectMapper) {
        return JacksonValueMapperFactory.builder()
                .withPrototype(objectMapper)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public InclusionStrategy inclusionStrategy() {
        return new DefaultInclusionStrategy() {
            @Override
            public boolean includeInputField(InputFieldInclusionParams params) {
                if (params.getElements().stream().anyMatch(this::isIgnored) ||
                        !isPackageAcceptable(params.getDeclaringType(), params.getElementDeclaringClass())) {
                    return false;
                }
                if(params.isDirectlyDeserializable() || params.isDeserializableInSubType()) {
                    return true;
                }
                // Always include collections even if there is no setter available
                for(AnnotatedElement element : params.getElements()) {
                    if(element instanceof Method) {
                        Class<?> returnType = ((Method) element).getReturnType();
                        if(Collection.class.isAssignableFrom(returnType) || Map.class.isAssignableFrom(returnType)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

}
