package com.greener.domain.model

enum class Status(val statusCode:Int) {
    SUCCESS(1),
    FAIL(-1),
    EXIST(1),
    NOT_EXIST(-1)
}