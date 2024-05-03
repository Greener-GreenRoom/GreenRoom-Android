package com.greener.data.source.remote

import com.greener.data.model.mypage.MyPageInfoDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.service.MyPageService
import com.greener.domain.model.ApiState
import javax.inject.Inject

class MyPageDataSource @Inject constructor(
    private val service: MyPageService
) {
    suspend fun getMyPageInfo():ApiState<ResponseFormDTO<MyPageInfoDTO>> {
        return try{
            val response = service.getMyPageInfo()
            if(response.responseDTO.output == 0) {
                ApiState.Success(response)
            }
            else {
                ApiState.Fail(response)
            }
        } catch (e:Exception) {
            ApiState.Exception(e)
        }

    }
}