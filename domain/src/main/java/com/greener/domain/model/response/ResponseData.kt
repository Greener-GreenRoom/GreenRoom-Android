package com.greener.domain.model.response

data class ResponseData<T>(
    val response: ResponseResult,
    val data: T?,
)
