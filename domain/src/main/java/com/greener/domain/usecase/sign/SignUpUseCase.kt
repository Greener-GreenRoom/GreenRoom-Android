package com.greener.domain.usecase.sign

import com.greener.domain.model.ApiState
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.sign.SignInfo
import com.greener.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: SignRepository,
) {
    suspend operator fun invoke(signInfo: SignInfo): ApiState<ResponseResult> {
        return repository.signUp(signInfo)
    }
}
