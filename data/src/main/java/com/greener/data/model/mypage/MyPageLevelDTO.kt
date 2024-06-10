package com.greener.data.model.mypage

import com.greener.data.model.GreenRoomItemDTO
import com.greener.domain.model.mypage.GradeTier
import com.greener.domain.model.mypage.MyLevelInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPageLevelDTO(
    @Json(name = "gradeDto") val gradeDto: GradeDTO,
    @Json(name = "nextLevelToGetItems") val nextLevelToGetItems: Int,
    @Json(name = "itemDtoList") val itemDtoList: List<GreenRoomItemDTO>
) {
    fun toDomain() =
        MyLevelInfo(gradeDto.toDomain(), findMyTier(gradeDto.currentLevel), nextLevelToGetItems, itemDtoList.map { it.toDomain() })

    private fun findMyTier(myLevel: Int): GradeTier {
        for (tier in GradeTier.entries) {
            if (tier.tierBegin <= myLevel && myLevel <= tier.tierEnd) {
                return tier
            }
        }
        return GradeTier.NONE
    }
}