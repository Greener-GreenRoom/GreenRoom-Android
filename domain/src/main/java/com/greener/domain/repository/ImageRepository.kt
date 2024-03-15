package com.greener.domain.repository

interface ImageRepository {
    suspend fun getImages(path: String?): Result<List<String>>
}
