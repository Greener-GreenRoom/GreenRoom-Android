package com.greener.data.service

import com.greener.data.model.mypage.MyPageInfoDTO
import com.greener.data.model.response.ResponseFormDTO
import retrofit2.http.GET

interface MyPageService {

    @GET("api/myPage")
    suspend fun getMyPageInfo():ResponseFormDTO<MyPageInfoDTO>
}