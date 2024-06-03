package com.greener.presentation.ui.mypage.withdraw

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.greener.domain.model.mypage.WithdrawReason
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentUserWithdrawReasonBinding
import com.greener.presentation.ui.base.BaseFragment

class UserWithdrawReasonFragment : BaseFragment<FragmentUserWithdrawReasonBinding>(
    FragmentUserWithdrawReasonBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initListener() {
        setWithdrawReasonRv()
        binding.btnWithdrawReasonNext.setOnClickListener{
            moveToWithdrawFinal()
        }

        binding.tbUserWithDrawToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setWithdrawReasonRv() {
        binding.rvWithdrawReason.adapter = WithdrawReasonRVAdapter(
            WithdrawReason.entries.toList(),
            { showInputArea() },
            { hideInputArea() })
    }

    private fun showInputArea() {
        binding.tlWithdrawReason.visibility = View.VISIBLE
    }

    private fun hideInputArea() {
        binding.tlWithdrawReason.visibility = View.INVISIBLE
    }

    private fun moveToWithdrawFinal() {
        findNavController().navigate(R.id.action_userWithdrawReasonFragment_to_userWithdrawFinalFragment)
    }


}