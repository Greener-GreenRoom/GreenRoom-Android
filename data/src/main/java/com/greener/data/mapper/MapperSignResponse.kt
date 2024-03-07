package com.greener.data.mapper

import com.greener.data.model.Token
import com.greener.data.model.response.ResponseForm
import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.TokenData


fun mapperTokenDataToDomain(responseForm: ResponseForm<Token>): ResponseData<TokenData> {

    val responseResult = ResponseResult(responseForm.response.output, responseForm.response.result)

    val tokenData = responseForm.data?.refreshToken?.let { refreshToken ->
        responseForm.data.accessToken.let { accessToken ->
            TokenData(
                refreshToken,
                accessToken
            )
        }
    }
    return ResponseData(responseResult, tokenData)
}

fun mapperEmailToDomain(responseForm: ResponseForm<String>): ResponseData<String> {
    val responseResult = ResponseResult(responseForm.response.output,responseForm.response.result)
    val email = responseForm.data

    return ResponseData(responseResult,email)

}