package com.greener.data.model.greenroom

import com.greener.domain.model.PlantInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlantInfoDTO(
    @Json(name = "plantId") val plaintId: Int?,
    @Json(name = "distributionName") val distributionName: String?,
    @Json(name = "plantAlias") val plantAlias: String?,
    @Json(name = "plantPictureUrl") val plantPictureUrl: String?,
    @Json(name = "plantExplanation") val plantExplanation: String?,
    @Json(name = "plantCategory") val plantCategory: String?,
) {
    fun toDomain(): PlantInfo {
        return PlantInfo(
            plaintId,
            distributionName,
            plantAlias,
            plantPictureUrl,
            plantExplanation,
            plantCategory,
        )
    }
}
