package com.greener.domain.model.sign

data class SignInfo(
    val email: String,
    val name: String,
    val provider: String,
    val photoUrl:String =""
)