package com.greener.data.source.remote

import android.util.Log
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
            Log.d("확인","response: ${response.data}")
            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e: Exception) {
            Log.d("확인","response 실패: ${e.message}")
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

    suspend fun editUserProfile(userDto:RequestBody,imagePart: MultipartBody.Part?):ApiState<ResponseFormDTO<UserAccountDTO>> {
        return try {
            val response = service.editUserProfile(imagePart,userDto)
            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e:Exception) {
            ApiState.Exception(e)
        }
    }
    suspend fun editUserProfile(userDto:RequestBody):ApiState<ResponseFormDTO<UserAccountDTO>> {
        return try {
            val response = service.editUserProfile(userDto)
            if (response.responseDTO.output == 0) {
                ApiState.Success(response)
            } else {
                ApiState.Fail(response)
            }
        } catch (e:Exception) {
            ApiState.Exception(e)
        }
    }

    suspend fun logout() {
        service.logout()
    }
}