package com.greener.domain.usecase.mypage

import com.greener.domain.repository.MyPageRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: MyPageRepository,
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
