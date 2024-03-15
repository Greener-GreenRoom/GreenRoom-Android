package com.greener.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setToken(accessToken: String)

    suspend fun setToken(accessToken: String, refreshToken: String)

    suspend fun setUserInfo(userEmail:String, provider:String, accessToken: String, refreshToken: String)
    suspend fun clearAll()
    fun getAccessToken(): Flow<String?>

    fun getRefreshToken():Flow<String?>
}