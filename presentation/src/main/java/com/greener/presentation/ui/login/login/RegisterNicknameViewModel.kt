package com.greener.presentation.ui.login.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.SignInfo
import com.greener.domain.model.Status
import com.greener.domain.usecase.datastore.SetLocalTokensUseCase
import com.greener.domain.usecase.sign.GetTokenUseCase
import com.greener.domain.usecase.sign.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterNicknameViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val setLocalTokensUseCase: SetLocalTokensUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _photoUrl = MutableStateFlow("")
    private val _nickname = MutableStateFlow("")
    private val _provider = MutableStateFlow("")

    private val _completeRegister = MutableStateFlow(false)
    val completeRegister = _completeRegister.asStateFlow()
    fun setEmail(email: String) {
        _email.update { email }
    }

    fun setPhotoUrl(photoUrl: String) {
        _photoUrl.update { photoUrl }
    }

    fun setNickName(nickName: String) {
        _nickname.update { nickName }
    }

    fun setProvider(provider: String) {
        _provider.update { provider }
    }

    fun signUp() {
        val signInfo = SignInfo(_email.value, _nickname.value, _provider.value, _photoUrl.value)
        viewModelScope.launch {
            signUpUseCase(signInfo).collect {
                if (it.output == Status.SUCCESS.statusCode) {
                    getTokenFromServer(signInfo)
                }
            }
        }
    }

    private fun getTokenFromServer(signInfo: SignInfo) {
        viewModelScope.launch {
            getTokenUseCase(signInfo.email).collect {
                if (it.response.output == Status.SUCCESS.statusCode) {
                    setTokens(it.data!!.accessToken, it.data!!.refreshToken)
                    _completeRegister.update { true }
                    Log.d("확인", "${_completeRegister.value}")
                } else {
                    Log.d("확인", "response.result: ${it.response.result}")
                    //TODO 에러 처리
                }
            }
        }

    }

    private fun setTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            setLocalTokensUseCase(accessToken, refreshToken)
        }
    }


}