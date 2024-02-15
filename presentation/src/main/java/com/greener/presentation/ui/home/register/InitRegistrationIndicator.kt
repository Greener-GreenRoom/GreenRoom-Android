package com.greener.presentation.ui.home.register

import android.content.Context
import com.greener.presentation.R

object InitRegistrationIndicator {
    fun initRegistrationIndicator(
        indicator : com.greener.presentation.databinding.ViewPlantRegistrationIndicatorBinding,
        context: Context,
        position: Int
    ) {
        when (position) {
            1 -> {
                indicator.viewRegistrationIndicatorFirst.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.setBackgroundColor(context.getColor(R.color.gray100))
                indicator.viewRegistrationIndicatorThird.setBackgroundColor(context.getColor(R.color.gray100))
                indicator.viewRegistrationIndicatorFourth.setBackgroundColor(context.getColor(R.color.gray100))
            }
            2 -> {
                indicator.viewRegistrationIndicatorFirst.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorThird.setBackgroundColor(context.getColor(R.color.gray100))
                indicator.viewRegistrationIndicatorFourth.setBackgroundColor(context.getColor(R.color.gray100))
            }
            3 -> {
                indicator.viewRegistrationIndicatorFirst.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorThird.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorFourth.setBackgroundColor(context.getColor(R.color.gray100))
            }
            4 -> {
                indicator.viewRegistrationIndicatorFirst.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorSecond.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorThird.setBackgroundColor(context.getColor(R.color.primary))
                indicator.viewRegistrationIndicatorFourth.setBackgroundColor(context.getColor(R.color.primary))
            }
        }
    }
}
