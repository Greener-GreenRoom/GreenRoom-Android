package com.greener.data.service

import androidx.datastore.preferences.protobuf.NullValue
import com.greener.data.model.mypage.MyPageInfoDTO
import com.greener.data.model.mypage.MyPageLevelDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.sign.request.UserAccountDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface MyPageService {

    @GET("api/myPage")
    suspend fun getMyPageInfo(): ResponseFormDTO<MyPageInfoDTO>

    @GET("api/myPage/grade")
    suspend fun getMyLevelInfo(): ResponseFormDTO<MyPageLevelDTO>

    @Multipart
    @PATCH("api/user")
    suspend fun editUserProfile(
        @Part userImage: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("imageUpdateFlag") imageUpdateFlag: RequestBody
    ): ResponseFormDTO<UserAccountDTO>

    @Multipart
    @PATCH("api/user")
    suspend fun editUserProfile(
        @Part("name") name: RequestBody,
        @Part("imageUpdateFlag") imageUpdateFlag: RequestBody
    ): ResponseFormDTO<UserAccountDTO>

    @DELETE("api/authenticate/logout")
    suspend fun logout()

    @DELETE("api/user/delete")
    suspend fun deleteUser():ResponseFormDTO<String?>
}