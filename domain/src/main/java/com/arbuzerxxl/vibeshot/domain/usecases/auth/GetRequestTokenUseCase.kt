package com.arbuzerxxl.vibeshot.domain.usecases.auth

import com.arbuzerxxl.vibeshot.domain.models.RequestTokenDomain
import com.arbuzerxxl.vibeshot.domain.repository.AuthRepository
import com.arbuzerxxl.vibeshot.domain.usecases.UseCaseWithoutParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetRequestTokenUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
) : UseCaseWithoutParams<RequestTokenDomain> {

    override suspend fun execute(): RequestTokenDomain =
        withContext(dispatcher) {
            authRepository.getRequestToken()
        }
}