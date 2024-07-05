package com.greener.domain.model

enum class ResponseCode(val codeNumber: Int, val message: String) {
    SUCCESS(0x0000, "성공"),
    FAILED(-0x0001, "실패했습니다. 다시 시도해주세요"),
    ERROR(-0x0002, "에러가 발생했습니다"),

    RESULT_NOT_FOUND(-0x1001, "결과를 찾을 수 없습니다"),
    ALREADY_EXIST(-0x2001, "이미 존재하는 데이터입니다"),
    ALREADY_EXIST_OTHER_OAUTH(-0x2002, "다른 ouath2가 존재합니다"),
    ALL_TOKEN_WERE_EXPIRED(-0x2003, "모든 토큰이 만료되었습니다"),
    TOKENS_NOT_FOUND(-0x2004, "토큰을 찾을 수 없습니다"),

    FAIL_DATA_PARSE(-0x3001, "데이터를 읽는데 실패하였습니다"),
    INVALID_CREDENTIALS(-0x4001, "invalid credentials"),
    INVALID_FORMAT(-0x5001, "유효하지 않은 형식입니다"),
}
