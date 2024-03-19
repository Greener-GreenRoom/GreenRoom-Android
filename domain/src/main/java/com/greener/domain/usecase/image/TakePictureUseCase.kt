package com.greener.domain.usecase.image

import com.greener.domain.repository.ImageRepository
import javax.inject.Inject

class TakePictureUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(): Unit =
        repository.takePicture()

}
