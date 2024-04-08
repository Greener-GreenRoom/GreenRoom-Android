package com.greener.domain.model.greenroom

import com.greener.domain.model.user.UserInfo

data class UserGreenRoomsInfo(
    val userInfo: UserInfo,
    val greenRoomsTotalInfo: List<GreenRoomTotalInfo>
)
