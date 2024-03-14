package com.greener.domain.model.greenroom

import com.greener.domain.model.PlantInfo

data class GreenRoomInfo(
    val greenRoomBaseInfo: GreenRoomBaseInfo,
    val plantInfo: PlantInfo
)
