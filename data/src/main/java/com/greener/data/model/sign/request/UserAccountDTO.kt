package com.greener.data.model.sign.request

import com.greener.domain.model.sign.UserAccountInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAccountDTO(
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "profileUrl") val photoUrl: String,
    @Json(name = "provider") val provider: String
){
    fun toDomain() = UserAccountInfo(name,email,photoUrl,provider)
}