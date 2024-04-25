package com.greener.data.model.greenroom

import com.greener.data.model.UserInfoDTO
import com.greener.domain.model.greenroom.GreenRoomItems
import com.greener.domain.model.greenroom.GreenRoomTodo
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@JsonClass(generateAdapter = true)
data class GreenRoomTotalInfoDTO(
    @Json(name = "greenroomInfo") val greenRoomInfo: GreenRoomInfoDTO,
    @Json(name = "greenroomItem") val greenRoomItems: List<GreenRoomItemDTO>,
    @Json(name = "greenroomTodo") val greenRoomTodos: List<GreenRoomTodoDTO>

) {
    fun toDomain(): GreenRoomTotalInfo {
        val greenRoomItemMap = hashMapOf(
            "shape" to "",
            "hair_accessory" to "",
            "glasses" to "",
            "background_shelf" to "",
            "background_window" to ""
        )

        greenRoomItems.forEach {
            greenRoomItemMap[it.itemType] = it.itemName
        }
        return GreenRoomTotalInfo(
            greenRoomInfo.toDomain(),
            greenRoomItemMap,
            toDate(greenRoomTodos)
        )
    }

    private fun toDate(dates: List<GreenRoomTodoDTO>): MutableList<GreenRoomTodo> {
        val today = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val filteredDates = dates.filter {
            val date = sdf.parse(it.todoDate)
            date.before(today)
        }

        return filteredDates.map {
            it.toDomain()
        }.toMutableList()
    }
}
