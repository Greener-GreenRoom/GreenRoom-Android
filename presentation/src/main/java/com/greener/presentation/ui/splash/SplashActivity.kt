package com.greener.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.greener.domain.model.Status
import com.greener.presentation.databinding.ActivitySplashBinding
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
        viewModel.checkMyTokens()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeLocalToken()
            }

        }
    }

    private suspend fun observeLocalToken() {
        viewModel.isLogin.collect {
            if (it == Status.FAIL.code) {
                moveToLoginActivity()
            } else if (it == Status.SUCCESS.code) {
                moveToMainActivity()
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