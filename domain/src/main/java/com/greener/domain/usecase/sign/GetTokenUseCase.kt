package com.greener.domain.usecase.sign

import com.greener.domain.model.ApiState
import com.greener.domain.model.auth.TokenData
import com.greener.domain.model.response.ResponseData
import com.greener.domain.repository.SignRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: SignRepository,
) {
    suspend operator fun invoke(email: String): ApiState<ResponseData<TokenData>> {
        return repository.getToken(email)
    }

    suspend operator fun invoke(): ApiState<ResponseData<TokenData>> {
        return repository.getToken()
    }
}
