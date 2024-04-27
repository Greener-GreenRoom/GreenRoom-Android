package com.greener.data.source.remote

import android.util.Log
import com.greener.data.model.greenroom.TodoCompleteDTO
import com.greener.data.model.greenroom.UserGreenRoomsInfoDTO
import com.greener.data.model.greenroom.request.CompleteTodoList
import com.greener.data.service.HomeGreenRoomService
import com.greener.domain.model.ApiState
import javax.inject.Inject


class HomeGreenRoomDataSource @Inject constructor(
    private val service: HomeGreenRoomService
) {
    suspend fun getUserGreenRoomList(): ApiState<UserGreenRoomsInfoDTO> {
        return try {
            val response = service.getUserGreenRoomList()
            if (response.responseDTO.output == 0) {
                ApiState.Success(response.data)
            } else {
                ApiState.Fail(response.data)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
    }

    suspend fun completeTodo(id: Int, todoList: List<Int>): ApiState<TodoCompleteDTO> {
        return try {
            val response = service.completeTodo(id, CompleteTodoList(todoList))
            if (response.responseDTO.output == 0) {
                ApiState.Success(response.data)
            } else {
                Log.d("확인", "Fail: ${response.responseDTO.output}\n${response.responseDTO.result}")
                ApiState.Fail(response.data)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
    }
}