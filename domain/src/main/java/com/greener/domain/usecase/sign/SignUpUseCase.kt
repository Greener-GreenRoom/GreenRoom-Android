package com.greener.domain.usecase.sign

import com.greener.domain.model.ApiState
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.sign.UserAccountInfo
import com.greener.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: SignRepository
) {
    suspend operator fun invoke(userAccountInfo: UserAccountInfo): ApiState<ResponseResult> {

        return repository.signUp(userAccountInfo)
    }
}