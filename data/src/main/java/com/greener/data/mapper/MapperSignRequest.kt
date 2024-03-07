package com.greener.data.mapper

import com.greener.data.model.request.SignUpRequestInfo
import com.greener.domain.model.SignInfo


inline fun mapperSignUpInfoToData(signInfo: SignInfo): SignUpRequestInfo {
    return SignUpRequestInfo(signInfo.name, signInfo.email,signInfo.photoUrl,signInfo.provider)
}