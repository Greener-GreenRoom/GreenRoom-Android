package com.greener.data.service

import com.greener.data.model.greenroom.GreenRoomTotalInfoDTO
import com.greener.data.model.greenroom.UserGreenRoomsInfoDTO
import com.greener.data.model.response.ResponseFormDTO
import retrofit2.http.GET

interface HomeGreenRoomService {

    @GET("greenrooms?filter=enabled&sort=desc&offset=10")
    suspend fun getUserGreenRoomList():ResponseFormDTO<UserGreenRoomsInfoDTO?>

}