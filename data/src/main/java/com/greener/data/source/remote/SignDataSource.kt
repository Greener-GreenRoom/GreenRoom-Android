package com.greener.data.source.remote

import android.util.Log
import com.greener.data.model.sign.request.SignUpRequestDTO
import com.greener.data.model.auth.TokenDTO
import com.greener.data.model.sign.request.AuthenticateRequestDTO
import com.greener.data.model.response.ResponseDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.service.SignService
import com.greener.domain.model.ApiState
import com.greener.domain.model.ExampleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


class SignDataSource @Inject constructor(
    private val service: SignService
) {
    suspend fun signUp(signUpRequestDTO: SignUpRequestDTO): ApiState<ResponseFormDTO<TokenDTO>> {
        Log.d(
            "확인",
            signUpRequestDTO.email + signUpRequestDTO.name + signUpRequestDTO.provider + signUpRequestDTO.photoUrl
        )
        return try {
            val response = service.signUp(signUpRequestDTO)
            if (response.responseDTO.output == SUCCESS) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }

    }

    suspend fun getToken(email: String): ApiState<ResponseFormDTO<TokenDTO?>> {
        return try {
            val response = service.getToken(email)

            if (response.responseDTO.output == SUCCESS) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }

    }


    suspend fun updateToken(authenticateInfo: AuthenticateRequestDTO): ApiState<ResponseFormDTO<TokenDTO?>> {
        return try {
            val response = service.updateToken(authenticateInfo)
            if (response.responseDTO.output == SUCCESS) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }


    }

    companion object {
        const val SUCCESS = 0
    }

}

