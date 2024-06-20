package com.greener.domain.model.auth

data class TokenData(
    val refreshToken: String,
    val accessToken: String,
)
