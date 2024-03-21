package com.viettq.querydsldynamicquery;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
/**
 * description: register the scalar type for being use in schema
 * */
@Configuration
public class GraphqlConfiguration {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return writingBuider -> writingBuider.scalar(ExtendedScalars.Date);
    }
}
