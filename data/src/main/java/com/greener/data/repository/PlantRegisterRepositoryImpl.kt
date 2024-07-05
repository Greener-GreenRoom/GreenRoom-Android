package com.greener.data.repository

import android.util.Log
import com.greener.data.source.remote.PlantRegisterDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.ResponseCode
import com.greener.domain.model.exception.SuccessNullException
import com.greener.domain.model.exception.UnknownException
import com.greener.domain.model.plant_register.PlantInformationData
import com.greener.domain.model.plant_register.PlantRegisterRequestData
import com.greener.domain.model.plant_register.PlantRegisterResponseData
import com.greener.domain.repository.PlantRegisterRepository
import javax.inject.Inject

class PlantRegisterRepositoryImpl @Inject constructor(
    private val dataSource: PlantRegisterDataSource,
) : PlantRegisterRepository {
    override suspend fun registerGreenRoom(
        plantRegisterRequestData: PlantRegisterRequestData,
        image: String?,
    ): Result<PlantRegisterResponseData> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlantInformation(
        sort: String?,
        offset: Int?,
    ): Result<List<PlantInformationData>> =
        when (val apiState = dataSource.getPlantInformation(sort, offset)) {
            is ApiState.Success -> {
                val data = apiState.result?.data
                Result.success(
                    data?.map {
                        PlantInformationData(
                            plantId = it.plantId,
                            distributionName = it.distributionName,
                            plantAlias = it.plantAlias,
                            plantPictureUrl = it.plantPictureUrl,
                            plantExplanation = it.plantExplanation,
                            plantCategory = it.plantCategory,
                        )
                    } ?: emptyList(),
                )
            }

            is ApiState.Fail -> {
                Result.failure(handlePlantRegisterFail(apiState.result?.responseDTO!!.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(apiState.t).checkException()))
            }
        }

    override suspend fun getPlantWateringTip(plantId: Long): Result<String> =
        when (val apiState = dataSource.getPlantWateringTip(plantId)) {
            is ApiState.Success -> {
                Result.success(apiState.result?.data ?: "")
            }
            is ApiState.Fail -> {
                Result.failure(handlePlantRegisterFail(apiState.result?.responseDTO!!.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(apiState.t).checkException()))
            }
        }

    override suspend fun isDuplicateGreenRoomNickname(nickname: String): Result<Boolean> =
        when (val apiState = dataSource.isDuplicateGreenRoomNickname(nickname)) {
            is ApiState.Success -> {
                val data = apiState.result?.data
                if (data == null) {
                    Result.failure(SuccessNullException())
                } else {
                    Result.success(data)
                }
            }
            is ApiState.Fail -> {
                Result.failure(handlePlantRegisterFail(apiState.result?.responseDTO!!.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(apiState.t).checkException()))
            }
        }

    private fun handlePlantRegisterFail(errorCode: Int): Exception {
        ResponseCode.entries.forEach {
            if (it.codeNumber == errorCode) {
                return Exception(it.message)
            }
        }
        return Exception("알 수 없는 에러가 발생하였습니다.")
    }


}
