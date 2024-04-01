package com.greener.presentation.model.registration

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantRegistrationInfo(
    val plantId: Long?,
    val nickname: String?,
    val lastWatering: Int?,
    val waterDuration: Int?,
    val shape: String?,
) : Parcelable
