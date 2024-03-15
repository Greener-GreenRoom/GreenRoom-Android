package com.greener.data.mapper

import com.greener.data.model.sign.request.SignUpRequestDTO
import com.greener.domain.model.sign.SignInfo


inline fun mapperSignUpInfoToData(signInfo: SignInfo): SignUpRequestDTO {
    return SignUpRequestDTO(signInfo.name, signInfo.email,signInfo.photoUrl,signInfo.provider)
}