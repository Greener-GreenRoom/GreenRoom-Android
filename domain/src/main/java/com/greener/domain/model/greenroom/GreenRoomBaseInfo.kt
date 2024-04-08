package com.greener.domain.model.greenroom

data class GreenRoomBaseInfo(
    val greenroomId: Int,
    val name: String,
    val period: Int,
    val memo: String?,
    val imgUrl: String?,
    val status: Boolean
)