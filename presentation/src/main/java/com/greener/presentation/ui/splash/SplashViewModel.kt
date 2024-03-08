package com.greener.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.Status
import com.greener.domain.usecase.datastore.GetLocalAccessTokenUseCase
import com.greener.domain.usecase.sign.CheckTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLocalAccessTokenUseCase: GetLocalAccessTokenUseCase,
    private val checkTokenUseCase: CheckTokenUseCase
) : ViewModel() {

    private val _isLogin = MutableStateFlow(Status.DEFAULT.statusCode)
    val isLogin = _isLogin.asStateFlow()

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()
    fun checkMyTokens() {
        viewModelScope.launch {
            delay(1000)
            checkTokenUseCase().collect {
                if (it.response.output == Status.FAIL.statusCode) {
                    _isLogin.update { Status.FAIL.statusCode }
                } else {
                    _isLogin.update { Status.SUCCESS.statusCode }
                }
            }
            delay(5000)
            _isLogin.update { Status.FAIL.statusCode }
        }
    }
}