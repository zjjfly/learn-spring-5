package com.github.zjjfly.spring5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.just;

/**
 * 另一种定义处理请求的路由和处理函数的方式
 *
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021/2/24
 */
@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> helloRouterFunction() {
        return route(
                GET("/hello"),
                request -> ok().body(just("Hello World!"), String.class))
                .andRoute(GET("/bye"), request -> ok().body(just("See ya!"), String.class));
    }

}
