package com.greener.domain.usecase.sign

import com.greener.domain.model.response.ResponseData
import com.greener.domain.repository.SignRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckTokenUseCase @Inject constructor(
    private val repository: SignRepository
) {
    suspend operator fun invoke(): Flow<ResponseData<String>> {
        return repository.checkToken()
    }
}