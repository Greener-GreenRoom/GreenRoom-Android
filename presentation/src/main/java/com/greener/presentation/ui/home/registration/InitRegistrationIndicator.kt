package com.greener.presentation.ui.home.registration

import android.content.Context
import com.greener.presentation.R

object InitRegistrationIndicator {
    fun initRegistrationIndicator(
        indicator: com.greener.presentation.databinding.ViewPlantRegistrationIndicatorBinding,
        context: Context,
        position: Int,
    ) {
        when (position) {
            1 -> {
                indicator.viewRegistrationIndicatorFirst.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.background.setTint(context.getColor(R.color.gray100))
                indicator.viewRegistrationIndicatorThird.background.setTint(context.getColor(R.color.gray100))
                indicator.viewRegistrationIndicatorFourth.background.setTint(context.getColor(R.color.gray100))
            }
            2 -> {
                indicator.viewRegistrationIndicatorFirst.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorThird.background.setTint(context.getColor(R.color.gray100))
                indicator.viewRegistrationIndicatorFourth.background.setTint(context.getColor(R.color.gray100))
            }
            3 -> {
                indicator.viewRegistrationIndicatorFirst.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorThird.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorFourth.background.setTint(context.getColor(R.color.gray100))
            }
            4 -> {
                indicator.viewRegistrationIndicatorFirst.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorThird.background.setTint(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorFourth.background.setTint(context.getColor(R.color.primary))
            }
        }
    }
}
