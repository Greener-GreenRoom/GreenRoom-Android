package com.greener.domain.model.exception

class RequestDeniedException(val permission: String): Exception() {
}
