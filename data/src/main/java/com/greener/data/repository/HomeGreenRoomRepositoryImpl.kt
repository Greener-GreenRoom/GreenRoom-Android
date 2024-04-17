package com.greener.data.repository

import com.greener.data.source.remote.HomeGreenRoomDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.greener.domain.model.greenroom.UserGreenRoomsInfo
import com.greener.domain.repository.HomeGreenRoomRepository
import javax.inject.Inject

class HomeGreenRoomRepositoryImpl @Inject constructor(
    private val dataSource: HomeGreenRoomDataSource
) : HomeGreenRoomRepository {
    override suspend fun getUserGreenRoomList(): ApiState<UserGreenRoomsInfo> {
        val response = dataSource.getUserGreenRoomList()

        return when (response) {
            is ApiState.Success -> {
                ApiState.Success(response.result?.toDomain())
            }

            is ApiState.Fail -> {
                ApiState.Fail(response.result?.toDomain())
            }

            is ApiState.Exception -> {
                ApiState.Exception(response.t)
            }
        }
    }
}