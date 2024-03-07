package com.greener.data.repository

import com.greener.data.mapper.mapperEmailToDomain
import com.greener.data.mapper.mapperSignUpInfoToData
import com.greener.data.mapper.mapperTokenDataToDomain
import com.greener.data.source.remote.SignDataSource
import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.SignInfo
import com.greener.domain.model.TokenData
import com.greener.domain.repository.SignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val dataSource: SignDataSource
) : SignRepository {
    override suspend fun signUp(signInfo: SignInfo): Flow<ResponseResult> {
        val signUpRequestInfo = mapperSignUpInfoToData(signInfo)
        val responseResult = dataSource.signUp(signUpRequestInfo).map {
            ResponseResult(it.response.output, it.response.result)
        }
        return responseResult
    }

    override suspend fun getToken(email: String):Flow<ResponseData<TokenData>> {
        val result = dataSource.getToken(email).map {
            mapperTokenDataToDomain(it)
        }
        return result
    }

    override suspend fun checkToken():Flow<ResponseData<String>> {
        val result = dataSource.checkToken().map {
            mapperEmailToDomain(it)
        }
        return result
    }
}