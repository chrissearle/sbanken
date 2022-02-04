package net.chrissearle.sbanken.model

data class Transfer(
    val fromAccountId: String,
    val toAccountId: String,
    val message: String,
    val amount: Double
)
