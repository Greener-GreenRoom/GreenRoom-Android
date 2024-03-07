package com.greener.domain.usecase.datastore

import com.greener.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetLocalTokensUseCase @Inject constructor(
    private val repository: DataStoreRepository
){
    suspend operator fun invoke(accessToken:String, refreshToken:String) {
        repository.setToken(accessToken, refreshToken)
    }
}