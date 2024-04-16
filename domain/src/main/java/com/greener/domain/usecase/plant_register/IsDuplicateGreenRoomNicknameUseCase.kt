package com.greener.domain.usecase.plant_register

import com.greener.domain.repository.PlantRegisterRepository
import javax.inject.Inject

class IsDuplicateGreenRoomNicknameUseCase @Inject constructor(
    private val repository: PlantRegisterRepository,
) {
    suspend operator fun invoke(nickname: String): Result<Boolean> =
        repository.isDuplicateGreenRoomNickname(nickname)
}
