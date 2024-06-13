package com.greener.data.source.remote

import com.greener.data.model.sign.request.SignUpRequestDTO
import com.greener.data.model.auth.TokenDTO
import com.greener.data.model.sign.request.AuthenticateRequestDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.service.SignService
import com.greener.domain.model.ApiState
import java.lang.Exception
import javax.inject.Inject


class SignDataSource @Inject constructor(
    private val service: SignService
) {
    suspend fun signUp(signUpRequestDTO: SignUpRequestDTO): ApiState<ResponseFormDTO<TokenDTO>> {
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

