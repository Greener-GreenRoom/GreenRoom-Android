package com.greener.presentation.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.presentation.model.Status
import com.greener.domain.usecase.datastore.SetLocalTokensUseCase
import com.greener.domain.usecase.sign.GetTokenUseCase
import com.greener.domain.usecase.sign.UpdateTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val setLocalTokensUseCase: SetLocalTokensUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _isLogin = MutableStateFlow(Status.DEFAULT.code)
    val isLogin = _isLogin.asStateFlow()

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

    fun updateMyTokens() {
        viewModelScope.launch {
            delay(1000)
            updateTokenUseCase().collect {
                if (it.response.output < Status.SUCCESS.code) {
                    Log.d("확인","토큰 그냥 실패")
                    _isLogin.update { Status.FAIL.code }
                } else {
                    Log.d("확인","토큰 성공")
                    setTokensAtLocal(it.data!!.accessToken, it.data!!.refreshToken)
                    _isLogin.update { Status.SUCCESS.code }
                }
            }
            delay(5000)
            _isLogin.update { Status.FAIL.code }
        }
    }

    private suspend fun setTokensAtLocal(accessToken: String, refreshToken: String) {
        setLocalTokensUseCase(accessToken, refreshToken)
    }

    private suspend fun getToken() {
        getTokenUseCase().collect {
            if (it.response.output == Status.SUCCESS.code) {
                setTokensAtLocal(it.data!!.accessToken, it.data!!.refreshToken)
                _isLogin.update { Status.SUCCESS.code }
            }
            else {
                _isLogin.update { Status.FAIL.code }
            }
        }
    }
}