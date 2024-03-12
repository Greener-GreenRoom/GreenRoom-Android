package com.greener.presentation.ui.login.login

import android.window.OnBackInvokedDispatcher
import com.greener.presentation.databinding.ActivityLoginBinding
import com.greener.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {
}
