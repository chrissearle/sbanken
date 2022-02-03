package net.chrissearle.sbanken.service

import net.chrissearle.sbanken.configuration.SbankenConfiguration
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.server.ResponseStatusException

data class Account(
    val accountId: String,
    val accountNumber: String,
    val name: String,
    val accountType: String,
    val available: Double,
    val balance: Double,
    val creditLimit: Double
)

data class AccountList(
    val availableItems: Int,
    val items: List<Account>
)

@Service
class AccountService(
    val authService: AuthService,
    val config: SbankenConfiguration,
    val webClientBuilder: WebClient.Builder
) {
    fun buildUrl(api: String) = "${config.apiBase}${config.apiVersion}$api"

    suspend fun getAccounts(): AccountList {
        val token = authService.getAuthHeader()

        return webClientBuilder
            .defaultHeaders { it.setBearerAuth(token) }
            .build()
            .get()
            .uri(buildUrl("/Accounts"))
            .awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    it.awaitBody<AccountList>()
                } else {
                    throw ResponseStatusException(it.statusCode(), it.awaitBody())
                }
            }
    }
}