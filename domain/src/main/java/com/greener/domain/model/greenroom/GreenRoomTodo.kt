package com.greener.domain.model.greenroom

import com.greener.domain.model.ActionTodo
import java.util.Date

data class GreenRoomTodo(
    val actionTodo: ActionTodo,
    val activity: String,
    val todoDate: Date,
)
