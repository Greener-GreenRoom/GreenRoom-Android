package com.greener.domain.usecase.sign

import com.greener.domain.model.auth.TokenData
import com.greener.domain.model.response.ResponseData
import com.greener.domain.repository.SignRepository
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(
    private val repository: SignRepository,
) {
    suspend operator fun invoke(): Result<ResponseData<TokenData>> {
        return repository.updateToken()
    }
}
