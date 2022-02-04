package net.chrissearle.sbanken.model

data class Account(
    val accountId: String,
    val accountNumber: String,
    val name: String,
    val accountType: String,
    val available: Double,
    val balance: Double,
    val creditLimit: Double
)