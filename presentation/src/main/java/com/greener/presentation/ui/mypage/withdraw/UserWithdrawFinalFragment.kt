package com.greener.presentation.ui.mypage.withdraw

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentUserWithdrawFinalBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserWithdrawFinalFragment : BaseFragment<FragmentUserWithdrawFinalBinding>(
    FragmentUserWithdrawFinalBinding::inflate
) {
    private val viewModel: UserWithdrawViewModel by viewModels()

    private val args: UserWithdrawFinalFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
        observeUiState()
        binding.cbUserWithdrawFinal.addOnCheckedStateChangedListener { checkBox, state ->
            if (checkBox.isChecked) {
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
        binding.btnUserWithdrawFinalConfirm.setOnClickListener {
            viewModel.deleteUser()
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

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it is UiState.Success) {
                        Toast.makeText(requireActivity(), WITHDRAW_SUCCESS, Toast.LENGTH_SHORT).show()
                        moveToSignActivity()
                    } else if (it is UiState.Error) {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun moveToSignActivity() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        const val WITHDRAW_SUCCESS = "회원 탈퇴신청이 완료되었습니다\n해당 계정은 확인 후 삭제 예정입니다"
    }
}