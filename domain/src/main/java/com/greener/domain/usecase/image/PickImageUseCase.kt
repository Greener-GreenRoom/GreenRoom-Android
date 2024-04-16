package com.greener.domain.usecase.image

import com.greener.domain.repository.ImageRepository
import javax.inject.Inject

class PickImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(): Result<String> =
        repository.pickImage()

}
