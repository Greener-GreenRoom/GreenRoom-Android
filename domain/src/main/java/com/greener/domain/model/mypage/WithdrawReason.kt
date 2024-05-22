package com.greener.domain.model.mypage

enum class WithdrawReason(val reason:String) {
    UNCOMFORTABLE("이용이 번거롭고 불편해요"),
    QUIT("식물 키우기를 그만뒀어요"),
    REJOIN("재가입해서 다시 하고 싶어요"),
    FREQUENCY("이용 빈도가 낮아요"),
    ERROR("오류가 많아요"),
    DIRECT_INPUT("직접 입력");

}