package com.greener.domain.usecase.image

import com.greener.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(path: String? = null): Result<List<String>> =
        repository.getImages(path)

}
