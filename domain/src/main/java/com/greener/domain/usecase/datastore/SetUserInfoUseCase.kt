package com.greener.domain.usecase.datastore

import com.greener.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetUserInfoUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(
        userEmail: String,
        provider: String,
        accessToken: String,
        refreshToken: String
    ) {
        repository.setUserInfo(userEmail, provider, accessToken, refreshToken)
    }
}