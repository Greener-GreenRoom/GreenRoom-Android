package com.greener.domain.repository

interface ImageRepository {
    suspend fun pickImage(): Result<String>
    suspend fun takePicture(): Result<String>
}
