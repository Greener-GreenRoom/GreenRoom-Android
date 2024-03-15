package com.greener.data.repository


import android.util.Log
import com.greener.data.mapper.mapperSignUpInfoToData
import com.greener.data.mapper.mapperTokenDataToDomain
import com.greener.data.source.local.AuthDataSource
import com.greener.data.source.remote.SignDataSource
import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.sign.SignInfo
import com.greener.domain.model.auth.TokenData
import com.greener.domain.repository.SignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val authDataSource: AuthDataSource
) : SignRepository {
    override suspend fun signUp(signInfo: SignInfo): Flow<ResponseResult> {
        val signUpRequestInfo = mapperSignUpInfoToData(signInfo)
        val responseResult = signDataSource.signUp(signUpRequestInfo).map {
            ResponseResult(it.responseDTO.output, it.responseDTO.result)
        }
        return responseResult
    }

    override suspend fun getToken(email: String): Flow<ResponseData<TokenData>> {
        val result = signDataSource.getToken(email).map {
            mapperTokenDataToDomain(it)
        }
        return result
    }

    override suspend fun getToken(): Flow<ResponseData<TokenData>> {
        val email = authDataSource.getUserEmail().single()
        val result = signDataSource.getToken(email).map {
            mapperTokenDataToDomain(it)
        }
        return result
    }

    override suspend fun updateToken(): Flow<ResponseData<TokenData>> {
        Log.d("확인","signRepository의 update 토큰 진입")
        val authenticateInfo = authDataSource.getAuthenticateInfo().first()
        Log.d("확인","authenticateInfo: $authenticateInfo")
        val result = signDataSource.updateToken(authenticateInfo).map { response ->
            mapperTokenDataToDomain(response)
        }
        return result
    }


}
