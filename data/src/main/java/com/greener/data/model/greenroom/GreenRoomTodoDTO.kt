package com.greener.data.model.greenroom

import com.greener.domain.model.ActionTodo
import com.greener.domain.model.greenroom.GreenRoomTodo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GreenRoomTodoDTO(
    @Json(name = "activity") val activity: String,
    @Json(name = "todoDate") val todoDate: String
) {
    fun toDomain(): GreenRoomTodo {
        val actionTodo:ActionTodo
        when(activity)  {
            ActionTodo.WATERING.activity -> {
                actionTodo =ActionTodo.WATERING
            }
            ActionTodo.RE_POT.activity-> {
                actionTodo = ActionTodo.RE_POT
            }
            ActionTodo.PRUNING.activity-> {
                actionTodo = ActionTodo.PRUNING
            }
            ActionTodo.NUTRITION.activity-> {
                actionTodo = ActionTodo.NUTRITION
            }
            else -> {
                actionTodo = ActionTodo.VENTILATION
            }

        }
        return GreenRoomTodo(actionTodo,activity, todoDate)
    }
}