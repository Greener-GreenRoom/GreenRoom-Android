package com.greener.presentation.ui.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.ApiState
import com.greener.domain.model.sign.UserAccountInfo
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
    private val setUserInfoUseCase: SetUserInfoUseCase,
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
        val userAccountInfo = UserAccountInfo(_nickname.value, _email.value, _photoUrl.value, _provider.value)
        viewModelScope.launch {
            _uiState.update { UiState.Loading }
            val responseResult = signUpUseCase(userAccountInfo)

            when (responseResult) {
                is ApiState.Success -> {
                    getTokenFromServer(userAccountInfo)
                }

                is ApiState.Fail -> {
                    _uiState.update { UiState.Fail }
                }

                is ApiState.Exception -> {
                    val errorMessage = responseResult.checkException()
                    _uiState.update { UiState.Error(errorMessage) }
                }
            }
        }
    }

    private suspend fun getTokenFromServer(userAccountInfo: UserAccountInfo) {
        val responseData = getTokenUseCase(userAccountInfo.email)
        when (responseData) {
            is ApiState.Success -> {
                setUserInfoAtLocal(
                    responseData.result.data!!.accessToken,
                    responseData.result.data!!.refreshToken,
                )
                _uiState.update { UiState.Success }
            }

            is ApiState.Fail -> {
                _uiState.update { UiState.Fail }
            }

            is ApiState.Exception -> {
                _uiState.update { UiState.Error(responseData.checkException()) }
            }
        }
    }

    private suspend fun setUserInfoAtLocal(
        accessToken: String,
        refreshToken: String,
    ) {
        setUserInfoUseCase(_email.value, _provider.value, accessToken, refreshToken)
    }
}
