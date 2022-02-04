package net.chrissearle.sbanken.model

data class TransactionList(
    val availableItems: Int,
    val items: List<Transaction>
)