package com.greener.data.service

import com.greener.data.model.greenroom.TodoCompleteDTO
import com.greener.data.model.greenroom.UserGreenRoomsInfoDTO
import com.greener.data.model.greenroom.request.CompleteTodoList
import com.greener.data.model.response.ResponseFormDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface HomeGreenRoomService {

    @GET("greenrooms?filter=enabled&sort=desc")
    suspend fun getUserGreenRoomList(): ResponseFormDTO<UserGreenRoomsInfoDTO?>

    @PATCH("greenrooms/{greenRoomId}/todo")
    suspend fun completeTodo(
        @Path(value = "greenRoomId") greenRoomId: Int,
        @Body todoList: CompleteTodoList,
    ): ResponseFormDTO<TodoCompleteDTO>
}
