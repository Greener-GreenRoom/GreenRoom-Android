package com.greener.data.model.greenroom

import com.squareup.moshi.Json

data class GreenRoomItemDTO(
    @Json(name = "itemType") val itemType: String,
    @Json(name = "itemName") val itemName: String,
)
