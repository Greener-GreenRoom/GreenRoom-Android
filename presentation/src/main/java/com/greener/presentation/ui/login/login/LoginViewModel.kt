package com.greener.presentation.ui.login.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.presentation.model.Status
import com.greener.domain.usecase.datastore.SetLocalTokensUseCase
import com.greener.domain.usecase.datastore.SetUserInfoUseCase
import com.greener.domain.usecase.sign.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val setLocalTokensUseCase: SetLocalTokensUseCase,
    private val setUserInfoUseCase: SetUserInfoUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _photoUrl = MutableStateFlow("")
    val photoUrl = _photoUrl.asStateFlow()

    private val _provider = MutableStateFlow("")
    val provider = _provider.asStateFlow()


    private val _accessToken = MutableStateFlow<String>("")
    val accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow("")
    val refreshToken = _refreshToken.asStateFlow()

    private val _isExistingUser = MutableStateFlow(Status.DEFAULT.code)
    val isExistingUser = _isExistingUser.asStateFlow()


    fun setEmail(email: String) {
        _email.update { email }
    }

    fun setName(name: String) {
        _name.update { name }
    }

    fun setPhotoUrl(photoUrl: String) {
        _photoUrl.update { photoUrl }
    }

    fun setProvider(provider: String) {
        _provider.update { provider }
    }

    fun getToken() {
        viewModelScope.launch {
            getTokenUseCase(_email.value)
                .collect {
                    if (it.response.output == Status.SUCCESS.code) {
                        _accessToken.value = it.data!!.accessToken
                        _refreshToken.value = it.data!!.refreshToken
                        Log.d("확인", "accessToken: ${_accessToken.value}")
                        Log.d("확인", "refreshToken: ${_refreshToken.value}")
                        setUserInfoAtLocal()
                        _isExistingUser.update { Status.SUCCESS.code }
                    } else {
                        Log.d("확인", "response.result: ${it.response.result}")
                        _isExistingUser.update { Status.FAIL.code }
                    }
                }
        }

    }

    private fun setTokensAtLocal(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            setLocalTokensUseCase(accessToken, refreshToken)
        }
    }

    private fun setUserInfoAtLocal() {
        viewModelScope.launch {
            setUserInfoUseCase(
                _email.value,
                _provider.value,
                _accessToken.value,
                _refreshToken.value
            )
        }
    }

}
