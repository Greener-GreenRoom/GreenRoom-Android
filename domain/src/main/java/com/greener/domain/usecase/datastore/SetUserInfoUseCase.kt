package com.greener.domain.usecase.datastore

import com.greener.domain.repository.AuthRepository
import javax.inject.Inject

class SetUserInfoUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        userEmail: String,
        provider: String,
        accessToken: String,
        refreshToken: String,
    ) {
        repository.setUserInfo(userEmail, provider, accessToken, refreshToken)
    }
}
