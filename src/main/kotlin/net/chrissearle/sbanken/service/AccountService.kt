package net.chrissearle.sbanken.service

import net.chrissearle.sbanken.configuration.SbankenConfiguration
import org.springframework.stereotype.Service
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

enum class ReservationType {
    NotReservation, VisaReservation, PurchaseReservation, AtmReservation
}

enum class SourceType {
    AccountStatement, Archive
}

data class CardDetails(
    val cardNumber: String?,
    val currencyAmount: Double,
    val currencyRate: Double,
    val merchantCategoryCode: String?,
    val merchantCategoryDescription: String?,
    val merchantCity: String?,
    val merchantName: String?,
    val originalCurrencyCode: String?,
    val purchaseDate: String,
    val transactionId: String
)

data class TransactionDetail(
    val formattedAccountNumber: String?,
    val transactionId: Int,
    val cid: String,
    val amountDescription: String,
    val receiverName: String?,
    val numericReference: Int,
    val payerName: String?,
    val registrationDate: String?
)

data class Transaction(
    val accountingDate: String,
    val interestDate: String?,
    val otherAccountNumber: String?,
    val otherAccountNumberSpecified: Boolean,
    val amount: Double,
    val text: String?,
    val transactionType: String?,
    val transactionTypeCode: Int,
    val transactionTypeText: String,
    val isReservation: Boolean,
    val reservationType: ReservationType?,
    val source: SourceType,
    val cardDetailsSpecified: Boolean,
    val cardDetails: CardDetails?,
    val transactionDetailSpecified: Boolean,
    val transactionDetail: TransactionDetail?
)

data class TransactionList(
    val availableItems: Int,
    val items: List<Transaction>
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
            .baseUrl(buildUrl("/Accounts"))
            .build()
            .get()
            .awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    it.awaitBody()
                } else {
                    throw ResponseStatusException(it.statusCode(), it.awaitBody())
                }
            }
    }

    suspend fun getAccount(id: String): Account {
        val token = authService.getAuthHeader()

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
                    it.awaitBody()
                } else {
                    throw ResponseStatusException(it.statusCode(), it.awaitBody())
                }
            }
    }

    suspend fun transactions(id: String): TransactionList {
        val token = authService.getAuthHeader()

        return webClientBuilder
            .defaultHeaders { it.setBearerAuth(token) }
            .baseUrl(buildUrl("/Transactions"))
            .build()
            .get()
            .uri {
                it
                    .path("/{id}")
                    .queryParam("length", "20")
                    .build(id)
            }
            .awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    it.awaitBody()
                } else {
                    throw ResponseStatusException(it.statusCode(), it.awaitBody())
                }
            }
    }
}