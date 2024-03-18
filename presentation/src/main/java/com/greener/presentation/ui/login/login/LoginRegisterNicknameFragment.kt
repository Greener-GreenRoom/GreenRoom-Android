package com.greener.presentation.ui.login.login


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentLoginRegisterNicknameBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginRegisterNicknameFragment : BaseFragment<FragmentLoginRegisterNicknameBinding>(
    FragmentLoginRegisterNicknameBinding::inflate
) {
    private val viewModel: RegisterNicknameViewModel by viewModels()

    private val args: LoginRegisterNicknameFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("확인", "${args.signInfo[0]}\n${args.signInfo[1]}")
        viewModel.setEmail(args.signInfo[0])
        viewModel.setPhotoUrl(args.signInfo[1])
        viewModel.setProvider(args.signInfo[2])
    }

    override fun initListener() {
        binding.etRegisterNickname.doOnTextChanged { _, _, _, _ ->
            binding.btnRegisterNicknameConfirm.isEnabled = validateNickname()
        }
        binding.tlRegisterNickname.setErrorIconOnClickListener {
            binding.etRegisterNickname.text!!.clear()
        }
        binding.btnRegisterNicknameConfirm.setOnClickListener {
            viewModel.setNickName(binding.etRegisterNickname.text.toString())
            viewModel.signUp()
        }
        observeRegisterComplete()
    }

    private fun observeRegisterComplete() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Success -> {
                        moveToMain()
                    }

                    is UiState.Fail -> {
                        Toast.makeText(activity, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Error -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Loading -> {

                    }

                    is UiState.Empty -> {

                    }
                }
            }
        }
    }

    private fun validateNickname(): Boolean {
        val value: String = binding.etRegisterNickname.text.toString()
        val regex = Regex("[가-힣a-zA-Z]+")

        return if (value.isEmpty() || value.length > 10) {
            binding.tlRegisterNickname.error = " "
            binding.tlRegisterNickname.isErrorEnabled = true
            false
        } else if (!regex.matches(value)) {
            binding.tlRegisterNickname.error = " "
            binding.tvRegisterNicknameRule.visibility = View.VISIBLE
            false
        } else {
            binding.tlRegisterNickname.error = null
            binding.tvRegisterNicknameRule.visibility = View.INVISIBLE
            binding.tlRegisterNickname.isErrorEnabled = false
            true
        }
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}