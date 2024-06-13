package com.greener.domain.usecase.datastore

import com.greener.domain.repository.AuthRepository
import javax.inject.Inject

class ClearAllDataStoreUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.clearAll()
    }
}