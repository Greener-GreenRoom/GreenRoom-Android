package com.greener.data.service

import com.greener.data.model.request.SignUpRequestInfo
import com.greener.data.model.Token
import com.greener.data.model.response.ResponseForm
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignService {

    @POST("api/user/signup")
    suspend fun signUp(@Body signInfo: SignUpRequestInfo): ResponseForm<Token>

    @GET("api/authenticate/{email}")
    suspend fun getToken(
        @Path("email") email: String
    ): ResponseForm<Token>

    @GET("api/user/info")
    suspend fun checkToken(
    ):ResponseForm<String>
}