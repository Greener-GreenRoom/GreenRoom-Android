package com.greener.domain.model.sign

data class UserAccountInfo(
    val name: String,
    val email: String,
    val photoUrl:String ="",
    val provider: String,
)