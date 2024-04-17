package com.greener.data.model.greenroom

import com.greener.data.model.UserInfoDTO
import com.greener.domain.model.greenroom.GreenRoomItems
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GreenRoomTotalInfoDTO(
    @Json(name = "greenroomInfo") val greenRoomInfo: GreenRoomInfoDTO,
    @Json(name = "greenroomItem") val greenRoomItems: List<GreenRoomItemDTO>,
    @Json(name = "greenroomTodo") val greenRoomTodos: List<GreenRoomTodoDTO>

) {
    fun toDomain(): GreenRoomTotalInfo {
        val greenRoomItemMap = HashMap<String, String>()

        greenRoomItems.forEach {
            greenRoomItemMap[it.itemType] = it.itemName
        }
        return GreenRoomTotalInfo(
            greenRoomInfo.toDomain(),
            greenRoomItemMap,
            greenRoomTodos.map { it.toDomain() }
        )
    }
}
