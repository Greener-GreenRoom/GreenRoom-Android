package com.greener.domain.usecase.mypage

import com.greener.domain.repository.MyPageRepository
import javax.inject.Inject

class DeleteUserUserCase @Inject constructor(
    private val repository: MyPageRepository
) {
    suspend operator fun invoke(): Result<Int> {
        return repository.deleteUser()
    }
}