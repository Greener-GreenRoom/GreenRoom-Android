package com.greener.data.repository

import com.greener.data.source.local.AuthDataSource
import com.greener.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
) : DataStoreRepository {
    override suspend fun setToken(accessToken: String) {
        dataSource.setToken(accessToken)
    }

    override suspend fun setToken(accessToken: String, refreshToken: String) {
        dataSource.setToken(accessToken, refreshToken)
    }

    override suspend fun setUserInfo(
        userEmail: String,
        provider: String,
        accessToken: String,
        refreshToken: String
    ) {
        dataSource.setUserInfo(userEmail, provider, accessToken, refreshToken)
    }

    override suspend fun clearAll() {
        dataSource.clearAll()
    }

    override fun getAccessToken(): Flow<String?> {
        return dataSource.getAccessToken()
    }

    override fun getRefreshToken(): Flow<String?> {
        return dataSource.getRefreshToken()
    }

}