package com.greener.domain.usecase.mypage

import com.greener.domain.model.mypage.MyLevelInfo
import com.greener.domain.repository.MyPageRepository
import javax.inject.Inject

class GetMyLevelInfoUseCase @Inject constructor(
    private val repository: MyPageRepository,
) {
    suspend operator fun invoke(): Result<MyLevelInfo> {
        return repository.getMyLevelInfo()
    }
}
