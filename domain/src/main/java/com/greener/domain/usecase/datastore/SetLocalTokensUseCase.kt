package com.greener.domain.usecase.datastore

import com.greener.domain.repository.AuthRepository
import javax.inject.Inject

class SetLocalTokensUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(accessToken:String, refreshToken:String) {
        repository.setToken(accessToken, refreshToken)
    }
}