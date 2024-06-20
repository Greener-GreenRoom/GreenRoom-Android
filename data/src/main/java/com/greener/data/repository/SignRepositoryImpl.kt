package com.greener.data.repository

import com.greener.data.model.auth.TokenDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.sign.request.UserAccountDTO
import com.greener.data.source.local.AuthDataSource
import com.greener.data.source.remote.SignDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.auth.TokenData
import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.sign.UserAccountInfo
import com.greener.domain.model.sign.SignInfo
import com.greener.domain.repository.SignRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val authDataSource: AuthDataSource,
) : SignRepository {
    override suspend fun signUp(signInfo: SignInfo): Result<ResponseResult> {
        val signUpRequestInfo = mapperSignUpInfoToData(signInfo)

        val responseFormDTO = signDataSource.signUp(signUpRequestInfo)
        return when (responseFormDTO) {
            is ApiState.Success -> {
                Result.success(
                    ResponseResult(
                        responseFormDTO.result!!.responseDTO.output,
                        responseFormDTO.result!!.responseDTO.result,
                    ),
                )
            }

            is ApiState.Fail -> {
                Result.failure(
                    handleSignFailure(responseFormDTO.result!!.responseDTO.output),
                )
            }

            is ApiState.Exception -> {
                Result.failure(responseFormDTO.t!!)
            }
        }
    }

    override suspend fun getToken(email: String): Result<ResponseData<TokenData>> {
        val responseFormDTO = signDataSource.getToken(email)
        return when (responseFormDTO) {
            is ApiState.Success -> {
                Result.success(mapperTokenDataToDomain(responseFormDTO.result!!))
            }

            is ApiState.Fail -> {
                Result.failure(handleSignFailure(responseFormDTO.result!!.responseDTO.output))
            }

            is ApiState.Exception -> {
                Result.failure(responseFormDTO.t!!)
            }
        }
    }

    override suspend fun getToken(): Result<ResponseData<TokenData>> {
        val email = authDataSource.getUserEmail().single()

        val responseFormDTO = signDataSource.getToken(email)

        return when (responseFormDTO) {
            is ApiState.Success -> {
                Result.success(mapperTokenDataToDomain(responseFormDTO.result!!))
            }

            is ApiState.Fail -> {
                Result.failure(handleSignFailure(responseFormDTO.result!!.responseDTO.output))
            }

            is ApiState.Exception -> {
                Result.failure(responseFormDTO.t!!)
            }
        }
    }

    override suspend fun updateToken(): Result<ResponseData<TokenData>> {
        val authenticateInfo = authDataSource.getAuthenticateInfo().first()
        val responseFormDTO = signDataSource.updateToken(authenticateInfo)

        return when (responseFormDTO) {
            is ApiState.Success -> {
                Result.success(mapperTokenDataToDomain(responseFormDTO.result!!))
            }

            is ApiState.Fail -> {
                Result.failure(handleSignFailure(responseFormDTO.result!!.responseDTO.output))
            }

            is ApiState.Exception -> {
                Result.failure(responseFormDTO.t!!)
            }
        }
    }

    private fun mapperTokenDataToDomain(responseFormDTO: ResponseFormDTO<TokenDTO?>): ResponseData<TokenData> {
        val responseResult =
            ResponseResult(responseFormDTO.responseDTO.output, responseFormDTO.responseDTO.result)

        val tokenData = responseFormDTO.data?.refreshToken?.let { refreshToken ->
            responseFormDTO.data.accessToken.let { accessToken ->
                TokenData(
                    refreshToken,
                    accessToken,
                )
            }
        }
        return ResponseData(responseResult, tokenData)
    }

    private fun mapperSignUpInfoToData(signInfo: SignInfo): SignUpRequestDTO {
        return SignUpRequestDTO(signInfo.name, signInfo.email, signInfo.photoUrl, signInfo.provider)
    }

    private fun handleSignFailure(errorCode: Int): Exception =
        Exception()
}
