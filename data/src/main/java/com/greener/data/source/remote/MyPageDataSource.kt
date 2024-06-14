package com.greener.data.source.remote

import com.greener.data.model.mypage.MyPageInfoDTO
import com.greener.data.model.mypage.MyPageLevelDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.sign.request.UserAccountDTO
import com.greener.data.service.MyPageService
import com.greener.domain.model.ApiState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MyPageDataSource @Inject constructor(
    private val service: MyPageService
) {
    suspend fun getMyPageInfo(): ApiState<ResponseFormDTO<MyPageInfoDTO>> {
        return try {
            val response = service.getMyPageInfo()

            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
    }

    suspend fun getMyLevelInfo(): ApiState<ResponseFormDTO<MyPageLevelDTO>> {
        return try {
            val response = service.getMyLevelInfo()
            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
    }

    suspend fun editUserProfile(
        name: RequestBody,
        imagePart: MultipartBody.Part?,
        imageUpdateFlag: RequestBody
    ): ApiState<ResponseFormDTO<UserAccountDTO>> {
        return try {
            val response = service.editUserProfile(imagePart, name, imageUpdateFlag)
            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
    }

    suspend fun editUserProfile(
        name: RequestBody,
        imageUpdateFlag: RequestBody
    ): ApiState<ResponseFormDTO<UserAccountDTO>> {
        return try {
            val response = service.editUserProfile(name, imageUpdateFlag)
            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
    }

    suspend fun logout() {
        service.logout()
    }

    suspend fun deleteUser(): ApiState<ResponseFormDTO<String?>> {
        return try {
            val response = service.deleteUser()
            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            ApiState.Exception(e)
        }
    }
}