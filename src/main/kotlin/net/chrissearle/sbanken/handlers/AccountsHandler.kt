package net.chrissearle.sbanken.handlers

import net.chrissearle.sbanken.service.AccountService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AccountsHandler(val accountService: AccountService) {
    suspend fun accounts(req: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait(accountService.getAccounts())
    }

    suspend fun transactions(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id")

        return ServerResponse.ok().bodyValueAndAwait(accountService.transactions(id))
    }

}