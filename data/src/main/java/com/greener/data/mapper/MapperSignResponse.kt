package com.greener.data.mapper

import com.greener.data.model.auth.TokenDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.domain.model.response.ResponseData
import com.greener.domain.model.response.ResponseResult
import com.greener.domain.model.auth.TokenData


fun mapperTokenDataToDomain(responseFormDTO: ResponseFormDTO<TokenDTO?>): ResponseData<TokenData> {

    val responseResult = ResponseResult(responseFormDTO.responseDTO.output, responseFormDTO.responseDTO.result)

    val tokenData = responseFormDTO.data?.refreshToken?.let { refreshToken ->
        responseFormDTO.data.accessToken.let { accessToken ->
            TokenData(
                refreshToken,
                accessToken
            )
        }
    }
    return ResponseData(responseResult, tokenData)
}
