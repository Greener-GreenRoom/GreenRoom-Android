package com.greener.domain.repository

import com.greener.domain.model.ApiState
import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.sign.SignInfo
import com.greener.domain.model.auth.TokenData
import kotlinx.coroutines.flow.Flow

interface SignRepository {

    suspend fun signUp(signInfo: SignInfo): Result<ResponseResult>

    suspend fun getToken(email:String): Result<ResponseData<TokenData>>

    suspend fun getToken(): Result<ResponseData<TokenData>>

    suspend fun updateToken():Result<ResponseData<TokenData>>
}