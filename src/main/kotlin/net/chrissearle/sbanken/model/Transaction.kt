package net.chrissearle.sbanken.model

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