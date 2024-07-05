package com.greener.data.model.greenroom

import com.greener.domain.model.greenroom.TodoCompleteInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoCompleteDTO(
    @Json(name = "level") val level: Int,
    @Json(name = "isLevelUpdated") val isLevelUpdated: Boolean,
    @Json(name = "increasingPoint") val increasingPoint: Int,
    @Json(name = "increasingCause") val increasingCause: String?,
) {
    fun toDomain(): TodoCompleteInfo =
        TodoCompleteInfo(level, isLevelUpdated, increasingPoint, increasingCause)
}
