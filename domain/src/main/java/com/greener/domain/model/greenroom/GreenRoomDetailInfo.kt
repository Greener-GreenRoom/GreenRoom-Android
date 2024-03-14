package com.greener.domain.model.greenroom

import com.greener.domain.model.GreenRoomItem
import com.greener.domain.model.GreenRoomTodo

data class GreenRoomDetailInfo(
    val greenRoomInfo: GreenRoomInfo,
    val greenRoomItem: GreenRoomItem,
    val greenRoomTodo: GreenRoomTodo

)
