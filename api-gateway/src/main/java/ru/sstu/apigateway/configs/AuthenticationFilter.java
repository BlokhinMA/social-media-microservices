package ru.sstu.apigateway.configs;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.sstu.apigateway.services.JwtUtils;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

    //@Autowired
    private final RouterValidator validator;

    //@Autowired
    private final JwtUtils jwtUtils;

    public AuthenticationFilter(RouterValidator validator, JwtUtils jwtUtils) {
        this.validator = validator;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            if (authMissing(request))
                return onError(exchange);

            final String token = request.getHeaders().getOrEmpty("Authorization").getFirst();

            if (jwtUtils.isExpired(token)) {
                return onError(exchange);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

}
