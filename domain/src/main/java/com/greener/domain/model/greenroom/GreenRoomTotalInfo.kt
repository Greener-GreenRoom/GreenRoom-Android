package com.greener.domain.model.greenroom

import com.greener.domain.model.user.UserInfo

data class GreenRoomTotalInfo(
    val greenRoomInfo: GreenRoomInfo,
    val greenRoomItems: List<GreenRoomItem>,
    val greenRoomTodos: List<GreenRoomTodo>
)
