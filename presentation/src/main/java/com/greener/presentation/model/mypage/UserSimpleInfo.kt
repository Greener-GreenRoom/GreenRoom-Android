package com.greener.presentation.model.mypage

import android.os.Parcelable
import com.greener.domain.model.mypage.GradeTier
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSimpleInfo(
    val nickName: String,
    val tier: GradeTier,
    val imageUrl: String?,
) : Parcelable
