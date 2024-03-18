package com.greener.data.repository


import android.util.Log
import com.greener.data.mapper.sign.mapperSignUpInfoToData
import com.greener.data.mapper.sign.mapperTokenDataToDomain
import com.greener.data.source.local.AuthDataSource
import com.greener.data.source.remote.SignDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.sign.SignInfo
import com.greener.domain.model.auth.TokenData
import com.greener.domain.repository.SignRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val authDataSource: AuthDataSource
) : SignRepository {
    override suspend fun signUp(signInfo: SignInfo): ApiState<ResponseResult> {
        val signUpRequestInfo = mapperSignUpInfoToData(signInfo)

        val responseFormDTO = signDataSource.signUp(signUpRequestInfo)
        return when (responseFormDTO) {

            is ApiState.Success -> {
                ApiState.Success(
                    ResponseResult(
                        responseFormDTO.result.responseDTO.output,
                        responseFormDTO.result.responseDTO.result
                    )
                )
            }

            is ApiState.Fail -> {
                ApiState.Fail(
                    ResponseResult(
                        responseFormDTO.result.responseDTO.output,
                        responseFormDTO.result.responseDTO.result
                    )
                )
            }

            is ApiState.Exception -> {
                responseFormDTO
            }
        }
    }

    override suspend fun getToken(email: String): ApiState<ResponseData<TokenData>> {

        val responseFormDTO = signDataSource.getToken(email)
        return when (responseFormDTO) {
            is ApiState.Success -> {
                ApiState.Success(mapperTokenDataToDomain(responseFormDTO.result))
            }

            is ApiState.Fail -> {
                ApiState.Fail(mapperTokenDataToDomain(responseFormDTO.result))
            }

            is ApiState.Exception -> {
                responseFormDTO
            }
        }
    }

    override suspend fun getToken(): ApiState<ResponseData<TokenData>> {
        val email = authDataSource.getUserEmail().single()

        val responseFormDTO = signDataSource.getToken(email)

        return when (responseFormDTO) {
            is ApiState.Success -> {
                ApiState.Success(mapperTokenDataToDomain(responseFormDTO.result))
            }

            is ApiState.Fail -> {
                ApiState.Fail(mapperTokenDataToDomain(responseFormDTO.result))
            }

            is ApiState.Exception -> {
                responseFormDTO
            }
        }
    }

    override suspend fun updateToken(): ApiState<ResponseData<TokenData>> {
        Log.d("확인", "signRepository의 update 토큰 진입")
        val authenticateInfo = authDataSource.getAuthenticateInfo().first()
        Log.d("확인", "authenticateInfo: $authenticateInfo")
        val responseFormDTO = signDataSource.updateToken(authenticateInfo)

        return when (responseFormDTO) {
            is ApiState.Success -> {
                ApiState.Success(mapperTokenDataToDomain(responseFormDTO.result))
            }

            is ApiState.Fail -> {
                ApiState.Fail(mapperTokenDataToDomain(responseFormDTO.result))
            }

            is ApiState.Exception -> {
                responseFormDTO
            }
        }
    }
}
