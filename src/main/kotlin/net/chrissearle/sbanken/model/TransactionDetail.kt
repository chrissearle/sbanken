package net.chrissearle.sbanken.model

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