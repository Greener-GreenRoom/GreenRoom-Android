package com.greener.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.greener.data.source.remote.PlantRegisterDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.ResponseCode
import com.greener.domain.model.exception.SuccessNullException
import com.greener.domain.model.plant_register.PlantInformationData
import com.greener.domain.model.plant_register.PlantRegisterRequestData
import com.greener.domain.model.plant_register.PlantRegisterResponseData
import com.greener.domain.repository.PlantRegisterRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class PlantRegisterRepositoryImpl @Inject constructor(
    private val dataSource: PlantRegisterDataSource,
    private val moshi: Moshi,
    private val context: Context,
) : PlantRegisterRepository {
    override suspend fun registerGreenRoom(
        plantRegisterRequestData: PlantRegisterRequestData,
        image: String?,
    ): Result<PlantRegisterResponseData> {
        val adapter = moshi.adapter(PlantRegisterRequestData::class.java)
        val plantRegisterRequestJson = adapter.toJson(plantRegisterRequestData)
        val plantRegisterRequestBody =
            plantRegisterRequestJson.toRequestBody("application/json".toMediaTypeOrNull())

        val imagePart: MultipartBody.Part? = if (image != null) {
            val imageFile = makeImageFile(Uri.parse(image))
            val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(
                PLANT_REGISTER_REQUEST_IMAGE,
                imageFile.name,
                imageRequestBody,
            )
        } else {
            null
        }

        return when (
            val apiState =
                dataSource.registerGreenRoom(plantRegisterRequestBody, imagePart)
        ) {
            is ApiState.Success -> {
                val data = apiState.result?.data
                if (data == null) {
                    Result.failure(SuccessNullException())
                } else {
                    Result.success(data.toDomain())
                }
            }

            is ApiState.Fail -> {
                Log.d("jomi", "fail : ${apiState.result?.responseDTO!!.output}")
                Result.failure(handlePlantRegisterFail(apiState.result?.responseDTO!!.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(apiState.t).checkException()))
            }
        }
    }

    override suspend fun getPlantInformation(
        sort: String?,
        offset: Int?,
    ): Result<List<PlantInformationData>> {
        return when (val apiState = dataSource.getPlantInformation(sort.toString(), offset)) {
            is ApiState.Success -> {
                val data = apiState.result?.data
                Result.success(
                    data?.map {
                        it.toDomain()
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

    // todo ImageRepository 로 이동해서 MyPageRepository에 있는 같은 함수랑 합치고 싶음
    private fun makeImageFile(uri: Uri): File {
        val bitmap = context.contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it)
        }
        val tempFile = File.createTempFile("image_", ".jpeg", context.cacheDir)

        FileOutputStream(tempFile).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, it)
        }
        return tempFile
    }

    companion object {
        private const val PLANT_REGISTER_REQUEST_IMAGE = "plant-register-request-image"
    }
}
