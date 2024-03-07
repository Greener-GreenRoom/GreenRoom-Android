package com.greener.presentation.ui.login.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.greener.presentation.ui.login.onboarding.LoginOnboardingFirstFragment
import com.greener.presentation.ui.login.onboarding.LoginOnboardingFourthFragment
import com.greener.presentation.ui.login.onboarding.LoginOnboardingSecondFragment
import com.greener.presentation.ui.login.onboarding.LoginOnboardingThirdFragment

class LoginOnBoardingVPAdapter(frag: FragmentActivity) : FragmentStateAdapter(frag) {

    override fun getItemCount(): Int {
        return ONBOARDING_COUNT
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) { // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
            0 -> LoginOnboardingFirstFragment()
            1 -> LoginOnboardingSecondFragment()
            2 -> LoginOnboardingThirdFragment()
            3 -> LoginOnboardingFourthFragment()
            else -> LoginOnboardingFirstFragment()
        }
    }

    companion object {
        const val ONBOARDING_COUNT = 4
    }
}