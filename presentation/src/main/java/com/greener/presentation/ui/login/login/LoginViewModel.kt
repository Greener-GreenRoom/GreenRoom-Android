package com.greener.presentation.ui.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.usecase.datastore.SetUserInfoUseCase
import com.greener.domain.usecase.sign.GetTokenUseCase
import com.greener.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val setUserInfoUseCase: SetUserInfoUseCase,
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

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

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
            _uiState.update { UiState.Loading }
            val responseData = getTokenUseCase(_email.value)
            if (responseData.isSuccess) {
                _accessToken.value = responseData.getOrNull()!!.data!!.accessToken
                _refreshToken.value = responseData.getOrNull()!!.data!!.refreshToken
                setUserInfoAtLocal()
                _uiState.update { UiState.Success }
            } else {
                _uiState.update { UiState.Fail }
                _uiState.update { UiState.Empty }
            }
        }
    }

    private suspend fun setUserInfoAtLocal() {
        setUserInfoUseCase(
            _email.value,
            _provider.value,
            _accessToken.value,
            _refreshToken.value,
        )
    }
}
