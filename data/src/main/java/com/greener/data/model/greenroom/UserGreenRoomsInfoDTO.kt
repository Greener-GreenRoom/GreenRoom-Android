package com.greener.data.model.greenroom

import com.greener.data.model.UserInfoDTO
import com.greener.domain.model.greenroom.UserGreenRoomsInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserGreenRoomsInfoDTO(
    @Json(name = "userInfo") val userInfo: UserInfoDTO,
    @Json(name = "greenroomTotalInfo") val greenRoomsTotalInfo: List<GreenRoomTotalInfoDTO>,
) {
    fun toDomain(): UserGreenRoomsInfo = UserGreenRoomsInfo(
        userInfo.toDomain(),
        greenRoomsTotalInfo.map { it.toDomain() },
    )
}
