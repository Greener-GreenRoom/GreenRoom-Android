package com.greener.presentation.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.greener.presentation.databinding.FragmentLoginBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initListener() {
        binding.btnLoginTempGoMain.setOnClickListener {
            moveToMain()
        }
        binding.googleLoginBtn.setOnClickListener {

        }
    }

    private fun moveToMain(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}
