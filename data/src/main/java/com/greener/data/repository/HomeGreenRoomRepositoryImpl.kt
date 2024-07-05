package com.greener.data.repository

import com.greener.data.source.remote.HomeGreenRoomDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.ResponseCode
import com.greener.domain.model.greenroom.TodoCompleteInfo
import com.greener.domain.model.greenroom.UserGreenRoomsInfo
import com.greener.domain.repository.HomeGreenRoomRepository
import javax.inject.Inject

class HomeGreenRoomRepositoryImpl @Inject constructor(
    private val dataSource: HomeGreenRoomDataSource,
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
                Result.failure(Exception(ApiState.Exception(response.t).checkException()))
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
                Result.failure(Exception(ApiState.Exception(response.t).checkException()))
            }
        }
    }

    private fun handleGreenRoomFailure(errorCode: Int): Exception {
        ResponseCode.entries.forEach {
            if (it.codeNumber == errorCode) {
                return Exception(it.message)
            }
        }
        return Exception("알 수 없는 에러가 발생하였습니다.")
    }
}
