package com.greener.domain.usecase.mypage

import com.greener.domain.model.sign.UserAccountInfo
import com.greener.domain.repository.MyPageRepository
import javax.inject.Inject

class EditUserProfileUseCase @Inject constructor(
    private val repository: MyPageRepository
) {
    suspend operator fun invoke(name:String,profileUrl:String?):Result<UserAccountInfo> {
        return repository.editUserProfile(name,profileUrl)
    }
}