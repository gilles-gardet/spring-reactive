package com.ggardet.reactive.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.lang.NonNull

@Configuration
class R2dbcConfiguration : AbstractR2dbcConfiguration() {
    @Value("\${database.name}")
    private val database: String? = null
    @Value("\${database.host}")
    private val host: String? = null
    @Value("\${database.port:5432}")
    private val port = 0
    @Value("\${database.username}")
    private val username: String? = null
    @Value("\${database.password}")
    private val password: String? = null

    @Bean
    @NonNull
    override fun connectionFactory(): ConnectionFactory {
        val builder = PostgresqlConnectionConfiguration.builder()
        val connectionConfiguration = builder
            .host(host!!)
            .port(port)
            .username(username!!)
            .password(password)
            .database(database)
            .build()
        return PostgresqlConnectionFactory(connectionConfiguration)
    }
}
