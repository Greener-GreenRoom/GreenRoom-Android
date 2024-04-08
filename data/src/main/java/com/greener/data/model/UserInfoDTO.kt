package com.greener.data.model

import com.greener.domain.model.user.UserInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoDTO(
    @Json(name = "userName") val userName: String,
    @Json(name = "userID") val userId: Int,
    @Json(name = "imgUrl") val imgUrl: String?,
    @Json(name = "period") val period: Int,
) {
    fun toDomain(): UserInfo = UserInfo(userName, userId, imgUrl, period)
}