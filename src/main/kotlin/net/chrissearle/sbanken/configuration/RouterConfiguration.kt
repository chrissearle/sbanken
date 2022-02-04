package net.chrissearle.sbanken.configuration

import net.chrissearle.sbanken.handlers.AccountsHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {
    @Bean
    fun routes(handler: AccountsHandler) = coRouter {
        GET("/accounts", handler::accounts)
        GET("/accounts/{id}", handler::account)
        GET("/accounts/{id}/transactions", handler::transactions)
    }
}

