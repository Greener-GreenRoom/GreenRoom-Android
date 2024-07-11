package com.greener.domain.model

enum class Sort(private val sort: String) {
    POPULAR("popular"), // 자주 키우는 순
    ;
    fun getPlantInformation(): String? =
        if (valueOf(sort) == POPULAR) sort else null
}
