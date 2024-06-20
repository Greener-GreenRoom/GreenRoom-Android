package com.greener.presentation.ui.mypage.withdraw

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.greener.domain.model.mypage.WithdrawReason
import com.greener.presentation.databinding.FragmentUserWithdrawReasonBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserWithdrawReasonFragment : BaseFragment<FragmentUserWithdrawReasonBinding>(
    FragmentUserWithdrawReasonBinding::inflate,
) {
    private lateinit var backPressedCallback: OnBackPressedCallback
    private lateinit var rvAdapter: WithdrawReasonRVAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
        setWithdrawReasonRv()
        binding.btnWithdrawReasonNext.setOnClickListener {
            moveToWithdrawFinal()
        }

        binding.tbUserWithDrawToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.lyUserWithdrawReason.setOnTouchListener { view, _ ->
            hideKeyboard(view)
            binding.etWithdrawReason.clearFocus()
            false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.etWithdrawReason.isFocused) {
                    binding.etWithdrawReason.clearFocus()
                } else {
                    isEnabled =
                        false // Disable the callback to let the activity handle the back press
                    requireActivity().onBackPressed() // Call the activity's onBackPressed method
                    isEnabled = true // Re-enable the callback
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    private fun setWithdrawReasonRv() {
        rvAdapter = WithdrawReasonRVAdapter(
            WithdrawReason.entries.toList(),
            { showInputArea() },
            { hideInputArea() },
        )
        binding.rvWithdrawReason.adapter = rvAdapter
    }

    private fun showInputArea() {
        binding.tlWithdrawReason.visibility = View.VISIBLE
    }

    private fun hideInputArea() {
        binding.tlWithdrawReason.visibility = View.INVISIBLE
    }

    private fun moveToWithdrawFinal() {
        val selectedReasons = rvAdapter.getWithdrawReasons()
        val reasons = mutableListOf<String>()

        for (reason in selectedReasons) {
            if (reason == WithdrawReason.DIRECT_INPUT) {
                reasons.add(binding.etWithdrawReason.text.toString())
                continue
            }
            reasons.add(reason.reason)
        }
        val action = UserWithdrawReasonFragmentDirections.actionUserWithdrawReasonFragmentToUserWithdrawFinalFragment(reasons.toTypedArray())
        findNavController().navigate(action)
    }

    private fun hideKeyboard(view: View) {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        binding.etWithdrawReason.clearFocus()
    }
}
