package com.greener.domain.model

enum class Status(val code:Int) {
    SUCCESS(0),
    FAIL(-1),
    DEFAULT(100),
    EXIST(0),
    NOT_EXIST(-1)
}