package com.greener.data.service

import com.greener.data.model.auth.TokenDTO
import com.greener.data.model.response.ResponseFormDTO
import com.greener.data.model.sign.request.AuthenticateRequestDTO
import com.greener.data.model.sign.request.UserAccountDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface SignService {

    @POST("api/user/signup")
    suspend fun signUp(@Body signInfo: UserAccountDTO): ResponseFormDTO<TokenDTO>

    @POST("api/authenticate")
    suspend fun updateToken(
        @Body singInfo: AuthenticateRequestDTO,
    ): ResponseFormDTO<TokenDTO?>

    @POST("api/authenticate/issue")
    suspend fun getToken(
        @Body userEmail: String,
    ): ResponseFormDTO<TokenDTO?>
}
