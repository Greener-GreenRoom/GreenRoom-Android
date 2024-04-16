package com.greener.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.greener.data.model.sign.request.AuthenticateRequestDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun setToken(accessToken: String) {
        dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun setToken(accessToken: String, refreshToken: String) {
        dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun setUserEmail(userEmail: String) {
        dataStore.edit {
            it[USER_EMAIL] = userEmail
        }
    }

    suspend fun setProvider(provider: String) {
        dataStore.edit {
            it[PROVIDER] = provider
        }
    }

    suspend fun setUserInfo(
        userEmail: String,
        provider: String,
        accessToken: String,
        refreshToken: String,
    ) {
        setUserEmail(userEmail)
        setProvider(provider)
        setToken(accessToken, refreshToken)
    }

    suspend fun clearAll() {
        dataStore.edit {
            it.clear()
        }
    }

    fun getAccessToken(): Flow<String> = dataStore.data.map {
        it[ACCESS_TOKEN] ?: ""
    }

    fun getRefreshToken(): Flow<String> = dataStore.data.map {
        it[REFRESH_TOKEN] ?: ""
    }

    fun getUserEmail(): Flow<String> = dataStore.data.map {
        it[USER_EMAIL] ?: ""
    }

    fun getProvider(): Flow<String?> = dataStore.data.map {
        it[PROVIDER] ?: ""
    }

    fun getAuthenticateInfo(): Flow<AuthenticateRequestDTO> = dataStore.data.map {
        AuthenticateRequestDTO(
            it[USER_EMAIL] ?: "",
            it[PROVIDER] ?: "",
            it[REFRESH_TOKEN] ?: "",
            it[ACCESS_TOKEN] ?: "",
        )
    }

    suspend fun testGetAuthenticateInfo(): AuthenticateRequestDTO {
        val a = dataStore.data.map {
            AuthenticateRequestDTO(
                getUserEmail().last(),
                getProvider().last(),
                getRefreshToken().last(),
                getAccessToken().last(),
            )
        }
        return a.last()
    }

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        val USER_EMAIL = stringPreferencesKey("userEmail")
        val PROVIDER = stringPreferencesKey("provider")
    }
}
