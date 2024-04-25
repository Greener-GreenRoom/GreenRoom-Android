package com.greener.domain.model.greenroom


data class GreenRoomTotalInfo(
    val greenRoomInfo: GreenRoomInfo,
    val greenRoomItems: HashMap<String,String>,
    val greenRoomTodos: MutableList<GreenRoomTodo>,
)
