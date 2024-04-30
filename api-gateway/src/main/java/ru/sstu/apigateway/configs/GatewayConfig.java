package ru.sstu.apigateway.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users", r -> r.path("/users/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://users"))
                .route("authentication", r -> r.path("/auth/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://authentication"))
                .route("albums", r -> r.path("/albums/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://albums"))
                .route("albums", r -> r.path("/photos/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://albums"))
                .route("communities", r -> r.path("/communities/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://communities"))
                /*.route("html-pages", r -> r.path("/html-pages/**")
                        //.filters(f -> f.filter(filter))
                        .uri("lb://html-pages"))*/
                .build();
    }

}
