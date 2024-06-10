package com.greener.presentation.ui.mypage.withdraw

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentUserWithdrawFinalBinding
import com.greener.presentation.ui.base.BaseFragment

class UserWithdrawFinalFragment: BaseFragment<FragmentUserWithdrawFinalBinding>(
    FragmentUserWithdrawFinalBinding::inflate
) {
    private val args: UserWithdrawFinalFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("확인","args; ${args.toString()}")
    }

    override fun initListener() {
        binding.cbUserWithdrawFinal.addOnCheckedStateChangedListener { checkBox, state ->
            if(checkBox.isChecked) {
                isChecked()
            } else {
                isNotChecked()
            }
        }

        binding.tbUserWithdrawFinalToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnUserWithdrawFinalBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun isChecked() {
        binding.btnUserWithdrawFinalConfirm.isEnabled = true
        binding.btnUserWithdrawFinalConfirm.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.red100)
    }

    private fun isNotChecked() {
        binding.btnUserWithdrawFinalConfirm.isEnabled = false
        binding.btnUserWithdrawFinalConfirm.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.gray200)
    }
}