package com.greener.data.repository

import com.greener.data.source.remote.HomeGreenRoomDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.greenroom.TodoCompleteInfo
import com.greener.domain.model.greenroom.UserGreenRoomsInfo
import com.greener.domain.repository.HomeGreenRoomRepository
import javax.inject.Inject

class HomeGreenRoomRepositoryImpl @Inject constructor(
    private val dataSource: HomeGreenRoomDataSource
) : HomeGreenRoomRepository {
    override suspend fun getUserGreenRoomList(): Result<UserGreenRoomsInfo?> {
        val response = dataSource.getUserGreenRoomList()

        return when (response) {
            is ApiState.Success -> {
                Result.success(response.result?.data?.toDomain())
            }

            is ApiState.Fail -> {
                Result.failure(handleGreenRoomFailure(response.result?.responseDTO!!.output))
            }

            is ApiState.Exception -> {
                Result.failure(response.t!!)
            }
        }
    }

    override suspend fun completeTodo(id: Int, todoList: List<Int>): Result<TodoCompleteInfo> {
        val response = dataSource.completeTodo(id, todoList)

        return when (response) {
            is ApiState.Success -> {
                Result.success(response.result?.data!!.toDomain())
            }

            is ApiState.Fail -> {
                Result.failure(handleGreenRoomFailure(response.result?.responseDTO!!.output))
            }

            is ApiState.Exception -> {
                Result.failure(response.t!!)
            }
        }
    }

    private fun handleGreenRoomFailure(errorCode: Int): Exception =
        Exception()
}