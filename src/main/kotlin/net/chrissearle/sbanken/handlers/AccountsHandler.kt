package net.chrissearle.sbanken.handlers

import net.chrissearle.sbanken.service.AccountService
import net.chrissearle.sbanken.service.AuthService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AccountsHandler(val accountService: AccountService) {
    suspend fun accounts(req: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait(accountService.getAccounts())
    }
}