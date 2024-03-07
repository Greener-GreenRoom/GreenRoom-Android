package com.greener.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setToken(accessToken:String) {
        dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun setToken(accessToken:String, refreshToken:String) {
        dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }
    suspend fun clearAll() {
        dataStore.edit {
            it.clear()
        }
    }
    fun getAccessToken(): Flow<String?> = dataStore.data.map {
        it[ACCESS_TOKEN]
    }

    fun getRefreshToken(): Flow<String?> = dataStore.data.map {
        it[REFRESH_TOKEN]
    }

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
    }


}