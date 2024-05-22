package com.greener.presentation.ui.mypage.withdraw

import android.os.Bundle
import android.view.View
import com.greener.domain.model.mypage.WithdrawReason
import com.greener.presentation.databinding.FragmentUserWithdrawReasonBinding
import com.greener.presentation.ui.base.BaseFragment

class UserWithdrawReasonFragment:BaseFragment<FragmentUserWithdrawReasonBinding>(
    FragmentUserWithdrawReasonBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initListener() {
        setWithdrawReasonRv()
    }

    private fun setWithdrawReasonRv() {
        binding.rvWithdrawReason.adapter = WithdrawReasonRVAdapter(WithdrawReason.entries.toList())
    }
}