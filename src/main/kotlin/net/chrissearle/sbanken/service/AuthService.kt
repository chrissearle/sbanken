package net.chrissearle.sbanken.service

import net.chrissearle.sbanken.configuration.SbankenConfiguration
import net.chrissearle.sbanken.model.Token
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.server.ResponseStatusException

@Component
class AuthService(
    val webClientBuilder: WebClient.Builder,
    val config: SbankenConfiguration
) {
    suspend fun getAuthHeader() = webClientBuilder
        .defaultHeaders { it.setBasicAuth(config.clientId, config.clientSecret) }
        .build()
        .post()
        .uri(config.authUrl)
        .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
        .awaitExchange<Token> {
            if (it.statusCode().is2xxSuccessful) {
                it.awaitBody()
            } else {
                throw ResponseStatusException(it.statusCode(), it.awaitBody())
            }
        }.accessToken
}