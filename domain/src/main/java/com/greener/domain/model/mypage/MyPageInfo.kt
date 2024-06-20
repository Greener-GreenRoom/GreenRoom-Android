package com.greener.domain.model.mypage

data class MyPageInfo(
    val simpleProfile: SimpleProfile,
    val gradeDto: Grade,
    val daysFromCreated: Int,
    val myTier: GradeTier,
)
