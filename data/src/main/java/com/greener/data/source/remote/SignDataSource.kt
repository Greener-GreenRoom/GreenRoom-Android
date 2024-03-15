package com.greener.data.source.remote

import android.util.Log
import com.greener.data.model.sign.request.SignUpRequestDTO
import com.greener.data.model.auth.TokenDTO
import com.greener.data.model.sign.request.AuthenticateRequestDTO
import com.greener.data.model.response.ResponseDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.service.SignService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SignDataSource @Inject constructor(
    private val service: SignService
) {
    suspend fun signUp(signUpRequestDTO: SignUpRequestDTO): Flow<ResponseFormDTO<TokenDTO>> = flow {
        Log.d(
            "확인",
            signUpRequestDTO.email + signUpRequestDTO.name + signUpRequestDTO.provider + signUpRequestDTO.photoUrl
        )
        emit(service.signUp(signUpRequestDTO))
    }

    suspend fun getToken(email: String): Flow<ResponseFormDTO<TokenDTO?>> = flow {
        emit(service.getToken(email))
    }


    suspend fun updateToken(authenticateInfo: AuthenticateRequestDTO): Flow<ResponseFormDTO<TokenDTO?>> =
        flow {

            emit(service.updateToken(authenticateInfo))
        }
}