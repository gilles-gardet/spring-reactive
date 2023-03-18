package com.ggardet.reactive.router

import com.ggardet.reactive.handler.PersonHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class PersonRouter {
    @Bean
    fun personRoutes(personHandler: PersonHandler): RouterFunction<ServerResponse> =
        route(GET("/")) { serverRequest: ServerRequest ->
            personHandler.renderPersonsAge(serverRequest)
        }
}
