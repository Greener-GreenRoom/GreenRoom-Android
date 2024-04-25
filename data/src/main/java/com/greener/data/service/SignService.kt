package com.greener.data.service

import com.greener.data.model.sign.request.SignUpRequestDTO
import com.greener.data.model.auth.TokenDTO
import com.greener.data.model.sign.request.AuthenticateRequestDTO
import com.greener.data.model.response.ResponseFormDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface SignService {

    @POST("api/user/signup")
    suspend fun signUp(@Body signInfo: SignUpRequestDTO):ResponseFormDTO<TokenDTO>

    @POST("api/authenticate")
    suspend fun updateToken(
        @Body singInfo: AuthenticateRequestDTO
    ): ResponseFormDTO<TokenDTO?>

    @POST("api/authenticate/issue")
    suspend fun getToken(
        @Body userEmail:String
    ):ResponseFormDTO<TokenDTO?>
}