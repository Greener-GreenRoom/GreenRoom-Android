package com.greener.data.source.remote

import android.util.Log
import com.greener.data.model.request.SignUpRequestInfo
import com.greener.data.model.Token
import com.greener.data.model.response.Response
import com.greener.data.model.response.ResponseForm
import com.greener.data.service.SignService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SignDataSource @Inject constructor(
    private val service: SignService
) {
    suspend fun signUp(signUpRequestInfo: SignUpRequestInfo): Flow<ResponseForm<Token>> = flow{
        Log.d("확인",signUpRequestInfo.email + signUpRequestInfo.name + signUpRequestInfo.provider + signUpRequestInfo.photoUrl)
        emit(service.signUp(signUpRequestInfo))
    }

    suspend fun getToken(email: String): Flow<ResponseForm<Token>> = flow {
        emit(service.getToken(email))
    }

    suspend fun checkToken(): Flow<ResponseForm<String>> = flow {
        try {
            val result = service.checkToken()
            emit(result)
        } catch (e: Exception) {
            //임시
            emit(ResponseForm(Response(-1,"fail"),""))
        }

    }
}