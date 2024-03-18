package com.greener.domain.model

import java.io.IOException
import java.net.SocketTimeoutException

sealed class ApiState<out T: Any> {
    data class Success<T: Any>(val result: T):ApiState<T>()
    data class Fail<T: Any>(val result:T): ApiState<T>()
    class Exception(val t: Throwable?):ApiState<Nothing>() {
        fun checkException():String {
            return when (t) {
                is SocketTimeoutException -> {
                    "네트워크가 불안정합니다."
                }

                is IOException -> {
                    "네트워크 연결을 확인해주세요"
                }

                else -> {
                    "알 수 없는 에러가 발생했습니다."
                }
            }
        }
    }



}