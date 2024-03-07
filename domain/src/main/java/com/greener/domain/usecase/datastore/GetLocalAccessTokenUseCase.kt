package com.greener.domain.usecase.datastore

import com.greener.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalAccessTokenUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    operator fun invoke(): Flow<String?> {
        return repository.getAccessToken()
    }
}