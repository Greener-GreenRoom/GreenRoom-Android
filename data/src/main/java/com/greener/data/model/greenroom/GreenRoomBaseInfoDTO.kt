package com.greener.data.model.greenroom

import com.greener.domain.model.greenroom.GreenRoomBaseInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GreenRoomBaseInfoDTO(
    @Json(name = "greenroomId") val greenRoomId: Int,
    @Json(name = "name") val name: String,
    @Json(name = "period") val period: Int,
    @Json(name = "memo") val memo: String?,
    @Json(name = "imgUrl") val imgUrl: String?,
    @Json(name = "status") val status: Boolean,
) {
    fun toDomain(): GreenRoomBaseInfo {
        return GreenRoomBaseInfo(
            greenRoomId,
            name,
            period,
            memo ?: "",
            imgUrl ?: "",
            status,
        )
    }
}
