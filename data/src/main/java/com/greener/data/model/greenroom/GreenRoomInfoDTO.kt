package com.greener.data.model.greenroom

import com.greener.domain.model.greenroom.GreenRoomInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GreenRoomInfoDTO(
    @Json(name = "greenroomBaseInfo") val greenRoomBaseInfo: GreenRoomBaseInfoDTO,
    @Json(name = "plantInfo") val plantInfo: PlantInfoDTO
) {
    fun toDomain(): GreenRoomInfo =
        GreenRoomInfo(greenRoomBaseInfo.toDomain(), plantInfo.toDomain())
}
