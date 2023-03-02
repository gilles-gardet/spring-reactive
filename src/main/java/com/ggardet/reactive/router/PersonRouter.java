package com.ggardet.reactive.router;

import com.ggardet.reactive.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonRouter {
    @Bean
    public RouterFunction<ServerResponse> personRoutes(PersonHandler personHandler) {
        return route(GET("/"), personHandler::names);
    }
}
