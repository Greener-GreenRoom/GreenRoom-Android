package com.greener.domain.repository

import com.greener.domain.model.mypage.ImageUpdateFlag
import com.greener.domain.model.mypage.MyPageInfo
import com.greener.domain.model.mypage.MyLevelInfo
import com.greener.domain.model.sign.UserAccountInfo

interface MyPageRepository {

    suspend fun getMyPageInfo(): Result<MyPageInfo>

    suspend fun getMyLevelInfo(): Result<MyLevelInfo>

    suspend fun editUserProfile(name: String, profileUrl: String?, imageUpdateFlag: String): Result<UserAccountInfo>

    suspend fun logout()
}