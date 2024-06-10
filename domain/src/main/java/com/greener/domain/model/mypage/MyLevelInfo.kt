package com.greener.domain.model.mypage

import com.greener.domain.model.GreenRoomItem

data class MyLevelInfo(
    val grade:Grade,
    val myTier:GradeTier,
    val nextLevelToGetItems:Int,
    val greenRoomItems: List<GreenRoomItem>
)
