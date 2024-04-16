package com.greener.data.source

import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.response.ResponseListFormDTO
import com.greener.domain.model.ApiState

object ResponseToApiState {
    private const val SUCCESS = 0

    fun <T>ResponseFormDTO<T>.toApiState(): ApiState<ResponseFormDTO<T>> =
        try {
            if (responseDTO.output == SUCCESS) {
                ApiState.Success(ResponseFormDTO(responseDTO, data))
            } else {
                ApiState.Fail(ResponseFormDTO(responseDTO, data))
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }

    fun <T>ResponseListFormDTO<T>.toApiState(): ApiState<ResponseListFormDTO<T>> =
        try {
            if (responseDTO.output == SUCCESS) {
                ApiState.Success(ResponseListFormDTO(responseDTO, data))
            } else {
                ApiState.Fail(ResponseListFormDTO(responseDTO, data))
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
}
