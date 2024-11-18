package com.waa.marketplace.utils;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("Admin APIs")
                .pathsToMatch("/api/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi sellerApi() {
        return GroupedOpenApi.builder()
                .group("Seller APIs")
                .pathsToMatch("/api/seller/**")
                .build();
    }

    @Bean
    public GroupedOpenApi buyerApi() {
        return GroupedOpenApi.builder()
                .group("Buyer APIs")
                .pathsToMatch("/api/buyer/**")
                .build();
    }
}
