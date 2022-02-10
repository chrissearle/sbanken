package net.chrissearle.sbanken.service

import mu.KotlinLogging
import net.chrissearle.sbanken.configuration.SbankenConfiguration
import net.chrissearle.sbanken.model.Account
import net.chrissearle.sbanken.model.AccountList
import net.chrissearle.sbanken.model.TransactionList
import net.chrissearle.sbanken.model.Transfer
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val logger = KotlinLogging.logger {}

@Service
class AccountService(
    val authService: AuthService,
    val config: SbankenConfiguration,
    val webClientBuilder: WebClient.Builder
) {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")

    fun buildUrl(api: String) = "${config.apiBase}${config.apiVersion}$api"

    suspend fun getAccounts(): AccountList {
        val token = authService.getAuthHeader()

        logger.debug { "Fetching accounts" }

        return webClientBuilder
            .defaultHeaders { it.setBearerAuth(token) }
            .baseUrl(buildUrl("/Accounts"))
            .build()
            .get()
            .awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    logger.debug { "Accounts retrieved" }

                    it.awaitBody()
                } else {
                    val errorBody = it.awaitBody<String>()

                    logger.error { "Accounts: ${it.statusCode()} gave $errorBody" }

                    throw ResponseStatusException(it.statusCode(), errorBody)
                }
            }

    }

    suspend fun getAccount(id: String): Account {
        val token = authService.getAuthHeader()

        logger.debug { "Fetching account $id" }

        return webClientBuilder
            .defaultHeaders { it.setBearerAuth(token) }
            .baseUrl(buildUrl("/Accounts"))
            .build()
            .get()
            .uri {
                it
                    .path("/{id}")
                    .build(id)
            }
            .awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    logger.debug { "Account $id retrieved" }

                    it.awaitBody()
                } else {
                    val errorBody = it.awaitBody<String>()

                    logger.error { "Account $id: ${it.statusCode()} gave $errorBody" }

                    throw ResponseStatusException(it.statusCode(), errorBody)
                }
            }
    }

    suspend fun transactions(id: String): TransactionList {
        val token = authService.getAuthHeader()

        logger.debug { "Fetching transactions for account $id" }

        return webClientBuilder
            .defaultHeaders { it.setBearerAuth(token) }
            .baseUrl(buildUrl("/Transactions"))
            .build()
            .get()
            .uri {
                it
                    .path("/{id}")
                    .queryParam("length", "100")
                    .queryParam("startDate", formatter.format(LocalDate.now().minusDays(365)))
                    .build(id)
            }
            .awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    logger.debug { "Transactions for account $id retrieved" }

                    it.awaitBody()
                } else {
                    val errorBody = it.awaitBody<String>()

                    logger.error { "Transactions for account $id: ${it.statusCode()} gave $errorBody" }

                    throw ResponseStatusException(it.statusCode(), errorBody)
                }
            }
    }

    suspend fun transfer(transfer: Transfer): String {
        val token = authService.getAuthHeader()

        logger.debug { "Transferring $transfer" }

        return webClientBuilder
            .defaultHeaders { it.setBearerAuth(token) }
            .baseUrl(buildUrl("/Transfers"))
            .build()
            .post()
            .body(BodyInserters.fromValue(transfer))
            .awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    logger.debug { "Transfer $transfer posted" }

                    "OK"
                } else {
                    val errorBody = it.awaitBody<String>()

                    logger.error { "Transfer $transfer: ${it.statusCode()} gave $errorBody" }

                    throw ResponseStatusException(it.statusCode(), errorBody)
                }
            }
    }
}