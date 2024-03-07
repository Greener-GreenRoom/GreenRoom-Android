package com.greener.domain.repository

import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.SignInfo
import com.greener.domain.model.TokenData
import kotlinx.coroutines.flow.Flow

interface SignRepository {

    suspend fun signUp(signInfo: SignInfo): Flow<ResponseResult>

    suspend fun getToken(email:String): Flow<ResponseData<TokenData>>

    suspend fun checkToken():Flow<ResponseData<String>>
}