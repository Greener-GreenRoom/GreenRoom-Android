package com.greener.domain.model

import java.util.Date

data class GreenRoomTodo(
    val todo: ActionTodo,
    val activity:String,
    val todoData:Date
)
