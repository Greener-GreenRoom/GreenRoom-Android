package com.greener.data.model.greenroom

import com.greener.domain.model.ActionTodo
import com.greener.domain.model.greenroom.GreenRoomTodo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.Locale

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
            ActionTodo.PRUNING.activity ->{
                actionTodo = ActionTodo.PRUNING
            }
            ActionTodo.NUTRITION.activity-> {
                actionTodo = ActionTodo.NUTRITION
            }
            ActionTodo.VENTILATION.activity -> {
                actionTodo = ActionTodo.VENTILATION
            }

            else -> {
                //TODO else 처리
                actionTodo = ActionTodo.PRUNING
            }

        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(todoDate)
        return GreenRoomTodo(actionTodo,activity, date)
    }
}