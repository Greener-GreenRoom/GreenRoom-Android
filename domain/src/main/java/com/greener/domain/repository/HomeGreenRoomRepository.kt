package com.greener.domain.repository

import com.greener.domain.model.ApiState
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.greener.domain.model.greenroom.TodoCompleteInfo
import com.greener.domain.model.greenroom.UserGreenRoomsInfo

interface HomeGreenRoomRepository {

    suspend fun getUserGreenRoomList(): Result<UserGreenRoomsInfo?>

    suspend fun completeTodo(id: Int, todoList: List<Int>): Result<TodoCompleteInfo>
}