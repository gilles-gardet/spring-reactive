package com.ggardet.reactive.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain

private const val ROLE_ADMIN = "ADMIN"
private const val ROLE_USER = "USER"

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val user = User.withUsername("user")
            .password("{noop}user")
            .roles(ROLE_USER)
            .build()
        val admin = User.withUsername("admin")
            .password("{noop}admin")
            .roles(ROLE_ADMIN)
            .build()
        return MapReactiveUserDetailsService(user, admin)
    }

    @Bean
    fun springSecurityFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
        serverHttpSecurity
            .authorizeExchange { exchanges -> exchanges.anyExchange().authenticated() }
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults()).build()
}
