package com.greener.domain.model.greenroom

data class TodoCompleteInfo(
    val level: Int,
    val isLevelUpdated: Boolean,
    val increasingPoint: Int,
    val increasingCause: String?,
)
