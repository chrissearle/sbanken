package net.chrissearle.sbanken.model

data class AccountList(
    val availableItems: Int,
    val items: List<Account>
)