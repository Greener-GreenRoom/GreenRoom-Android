package com.greener.domain.model.greenroom

data class GreenRoomBaseInfo(
    val greenroomId: Int,
    val imgUrl: String?,
    val memo: String?,
    val name: String,
    val period: Int,
    val status: Boolean
)