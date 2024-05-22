package com.greener.domain.model.mypage

data class Grade(
    val description: String,
    val gradeImageUrl: String?,
    val requiredSeedToNextLevel: Int,
    val currentSeed:Int,
    val nextLevelSeed: Int,
    val currentLevel: Int
)
