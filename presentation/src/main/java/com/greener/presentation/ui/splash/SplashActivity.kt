package com.greener.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.greener.presentation.databinding.ActivitySplashBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseActivity
import com.greener.presentation.ui.login.login.LoginActivity
import com.greener.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(
    ActivitySplashBinding::inflate
) {
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initListener() {
        viewModel.updateMyTokens()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeLocalToken()
            }

        }
    }

    private suspend fun observeLocalToken() {
        viewModel.uiState.collect {
            when(it) {
                is UiState.Success -> {
                    moveToMainActivity()
                }
                is UiState.Fail -> {
                    moveToLoginActivity()
                }
                is UiState.Error -> {
                    Toast.makeText(applicationContext,it.message,Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading ->{

                }
                is UiState.Empty -> {

                }
            }
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}