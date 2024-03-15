package com.greener.data.interceptor

import android.util.Log
import com.greener.data.source.local.AuthDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: AuthDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String? = runBlocking {
            Log.d("확인","runBlocking 코드 진입")
            val accessToken = dataStore.getAccessToken().firstOrNull()
            Log.d("확인","여기는 인터셉터 " +accessToken.toString())
            accessToken
        }
        val request = chain.request().newBuilder().header(AUTHORIZATION, "Bearer $token").build()
        return chain.proceed(request)
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}