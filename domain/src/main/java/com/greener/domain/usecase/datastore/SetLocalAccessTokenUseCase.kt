package com.greener.domain.usecase.datastore

import com.greener.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetLocalAccessTokenUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(accessToken: String) {
        repository.setToken(accessToken)
    }
}