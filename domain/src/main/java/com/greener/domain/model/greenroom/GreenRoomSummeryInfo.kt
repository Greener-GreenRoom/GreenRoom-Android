package com.greener.domain.model.greenroom

import com.greener.domain.model.greenroom.GreenRoomInfo

data class GreenRoomSummeryInfo(
    val greenRoomInfo: GreenRoomInfo,
    val shape:String,
    val todoNum:Int
)
