package com.greener.data.model

import com.greener.domain.model.GreenRoomItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GreenRoomItemDTO(
    @Json(name = "itemType") val itemType: String,
    @Json(name = "itemName") val itemName: String
) {
    fun toDomain(): GreenRoomItem {
        return GreenRoomItem(itemType, itemName)
    }
}