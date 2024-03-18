package com.greener.presentation.ui.login.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.ApiState
import com.greener.domain.model.sign.SignInfo
import com.greener.domain.usecase.datastore.SetUserInfoUseCase
import com.greener.domain.usecase.sign.GetTokenUseCase
import com.greener.domain.usecase.sign.SignUpUseCase
import com.greener.presentation.model.UiState
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
    private val setUserInfoUseCase: SetUserInfoUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _photoUrl = MutableStateFlow("")
    private val _nickname = MutableStateFlow("")
    private val _provider = MutableStateFlow("")

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()
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
            _uiState.update { UiState.Loading }
            val responseResult = signUpUseCase(signInfo)

            when (responseResult) {
                is ApiState.Success -> {
                    getTokenFromServer(signInfo)
                    _uiState.update { UiState.Success }
                }
                is ApiState.Fail -> {
                    Log.d("확인", "fail")
                    _uiState.update { UiState.Fail }
                }
                is ApiState.Exception -> {
                    val errorMessage = responseResult.checkException()
                    _uiState.update { UiState.Error(errorMessage ) }
                }

            }
        }
    }

    private fun getTokenFromServer(signInfo: SignInfo) {
        viewModelScope.launch {

            val responseData = getTokenUseCase(signInfo.email)

            when (responseData) {
                is ApiState.Success -> {
                    setUserInfoAtLocal(
                        responseData.result.data!!.accessToken,
                        responseData.result.data!!.refreshToken
                    )
                    _uiState.update { UiState.Success }
                }

                is ApiState.Fail -> {
                    Log.d("확인", "다시 시도")
                    _uiState.update { UiState.Fail }
                }

                is ApiState.Exception -> {
                    _uiState.update { UiState.Error(responseData.checkException()) }
                }
            }

        }

    }

    private fun setUserInfoAtLocal(
        accessToken: String,
        refreshToken: String
    ) {
        viewModelScope.launch {
            setUserInfoUseCase(_email.value, _provider.value, accessToken, refreshToken)
        }
    }
}