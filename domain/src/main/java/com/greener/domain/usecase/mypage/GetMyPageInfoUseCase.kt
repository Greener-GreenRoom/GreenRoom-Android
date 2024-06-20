package com.greener.domain.usecase.mypage

import com.greener.domain.model.mypage.MyPageInfo
import com.greener.domain.repository.MyPageRepository
import javax.inject.Inject

class GetMyPageInfoUseCase @Inject constructor(
    private val repository: MyPageRepository,
) {
    suspend operator fun invoke(): Result<MyPageInfo> {
        return repository.getMyPageInfo()
    }
}
