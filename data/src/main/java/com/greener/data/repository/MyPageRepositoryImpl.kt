package com.greener.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
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
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class MyPageRepositoryImpl @Inject constructor(
    private val dataSource: MyPageDataSource,
    private val moshi: Moshi,
    private val context: Context
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
                Result.failure(response.t!!)
            }
        }
    }

    override suspend fun editUserProfile(
        name: String,
        profileUrl: String?,
        imageUpdateFlag: String
    ): Result<UserAccountInfo> {
        val imagePart: MultipartBody.Part?
        val response: ApiState<ResponseFormDTO<UserAccountDTO>>

        if (imageUpdateFlag == ImageUpdateFlag.UPDATE.name) {
            val imageFile = makeImageFile(Uri.parse(profileUrl))
            Log.d("확인", "imageFIle: $imageFile")
            imagePart = makeMultiPart(imageFile)

            response = dataSource.editUserProfile(
                name.toRequestBody(),
                imagePart,
                imageUpdateFlag.toRequestBody()
            )
            Log.d("확인","resposne:${response.toString()}")
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
                Result.failure(response.t!!)
            }
        }
    }

    override suspend fun logout() {
        dataSource.logout()
    }

    private fun makeImageFile(uri: Uri): File {
        // 파일 스트림으로 uri로 접근해 비트맵을 디코딩
        val bitmap = context.contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it)
        }
        // 캐시 파일 생성
        val tempFile = File.createTempFile("image_", ".jpeg", context.cacheDir)
        Log.d("확인", "절대 경로 : ${tempFile.absolutePath}")
        Log.d("확인", "name : ${tempFile.name}")

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
            Log.d("확인","delete 성공")
            file.delete()
        } else {
            Log.d("확인","delete 실패")
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
        return Exception("알 수 없는 에러가 발생하였습니다.")
    }
}
