package net.chrissearle.sbanken.model

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