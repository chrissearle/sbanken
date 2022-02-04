package net.chrissearle.sbanken.handlers

import net.chrissearle.sbanken.service.AccountService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AccountsHandler(val accountService: AccountService) {
    suspend fun accounts(req: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyValueAndAwait(accountService.getAccounts())

    suspend fun account(req: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyValueAndAwait(accountService.getAccount(req.pathVariable("id")))

    suspend fun transactions(req: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyValueAndAwait(accountService.transactions(req.pathVariable("id")))

    suspend fun transfer(req: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyValueAndAwait(accountService.transfer(req.awaitBody()))
}