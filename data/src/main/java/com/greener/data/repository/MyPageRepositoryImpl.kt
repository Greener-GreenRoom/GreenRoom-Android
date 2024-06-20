package com.greener.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.greener.data.R
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.sign.request.UserAccountDTO
import com.greener.data.source.remote.MyPageDataSource
import com.greener.domain.model.ApiState
import com.greener.domain.model.ResponseCode
import com.greener.domain.model.mypage.ImageUpdateFlag
import com.greener.domain.model.mypage.MyLevelInfo
import com.greener.domain.model.mypage.MyPageInfo
import com.greener.domain.model.sign.UserAccountInfo
import com.greener.domain.repository.MyPageRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val dataSource: MyPageDataSource,
    private val moshi: Moshi,
    private val context: Context,
) : MyPageRepository {
    override suspend fun getMyPageInfo(): Result<MyPageInfo> {
        val response = dataSource.getMyPageInfo()

        return when (response) {
            is ApiState.Success -> {
                Result.success(response.result.data!!.toDomain())
            }

            is ApiState.Fail -> {
                Result.failure(handleMyPageFailure(response.result.responseDTO.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(response.t).checkException()))
            }
        }
    }

    override suspend fun getMyLevelInfo(): Result<MyLevelInfo> {
        val response = dataSource.getMyLevelInfo()

        return when (response) {
            is ApiState.Success -> {
                Result.success(response.result.data!!.toDomain())
            }

            is ApiState.Fail -> {
                Result.failure(handleMyPageFailure(response.result.responseDTO.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(response.t).checkException()))
            }
        }
    }

    override suspend fun editUserProfile(
        name: String,
        profileUrl: String?,
        imageUpdateFlag: String,
    ): Result<UserAccountInfo> {
        val imagePart: MultipartBody.Part?
        val response: ApiState<ResponseFormDTO<UserAccountDTO>>

        if (imageUpdateFlag == ImageUpdateFlag.UPDATE.name) {
            val imageFile = makeImageFile(Uri.parse(profileUrl))
            imagePart = makeMultiPart(imageFile)

            response = dataSource.editUserProfile(
                name.toRequestBody(),
                imagePart,
                imageUpdateFlag.toRequestBody(),
            )
            deleteFileFromCache(context, imageFile.name)
        } else {
            response =
                dataSource.editUserProfile(name.toRequestBody(), imageUpdateFlag.toRequestBody())
        }

        return when (response) {
            is ApiState.Success -> {
                Result.success(response.result.data!!.toDomain())
            }

            is ApiState.Fail -> {
                Result.failure(handleMyPageFailure(response.result.responseDTO.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(response.t).checkException()))
            }
        }
    }

    override suspend fun logout() {
        dataSource.logout()
    }

    override suspend fun deleteUser(): Result<Int> {
        val response = dataSource.deleteUser()

        return when (response) {
            is ApiState.Success -> {
                Result.success(response.result.responseDTO.output)
            }

            is ApiState.Fail -> {
                Result.failure(handleMyPageFailure(response.result.responseDTO.output))
            }

            is ApiState.Exception -> {
                Result.failure(Exception(ApiState.Exception(response.t).checkException()))
            }
        }
    }

    private fun makeImageFile(uri: Uri): File {
        // 파일 스트림으로 uri로 접근해 비트맵을 디코딩
        val bitmap = context.contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it)
        }
        // 캐시 파일 생성
        val tempFile = File.createTempFile("image_", ".jpeg", context.cacheDir)

        // 파일 스트림을 통해 파일에 비트맵 저장
        FileOutputStream(tempFile).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, it)
        }
        return tempFile
    }

    private fun deleteFileFromCache(context: Context, fileName: String): Boolean {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    private fun makeMultiPart(imageFile: File): MultipartBody.Part {
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("imageFile", imageFile.name, imageRequestBody)
    }

    private fun handleMyPageFailure(errorCode: Int): Exception {
        ResponseCode.entries.forEach {
            if (it.codeNumber == errorCode) {
                return Exception(it.message)
            }
        }
        return Exception(context.resources.getString(R.string.unknownError))
    }
}
