package com.greener.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.ApiState
import com.greener.domain.usecase.datastore.SetLocalTokensUseCase
import com.greener.domain.usecase.sign.UpdateTokenUseCase
import com.greener.presentation.model.UiState
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
    private val setLocalTokensUseCase: SetLocalTokensUseCase
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun updateMyTokens() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading }
            delay(1000)
            val responseData = updateTokenUseCase()

            when (responseData) {
                is ApiState.Success -> {
                    setTokensAtLocal(
                        responseData.result!!.data!!.accessToken,
                        responseData.result!!.data!!.refreshToken
                    )
                    _uiState.update { UiState.Success }
                }

                is ApiState.Fail -> {
                    _uiState.update { UiState.Fail }
                }

                is ApiState.Exception -> {
                    val errorMessage = responseData.checkException()
                    _uiState.update { UiState.Error(errorMessage) }
                }
            }
            delay(5000)

            _uiState.update { UiState.Fail }
        }

    }

    private suspend fun setTokensAtLocal(accessToken: String, refreshToken: String) {
        setLocalTokensUseCase(accessToken, refreshToken)
    }
}