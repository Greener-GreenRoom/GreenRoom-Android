package com.greener.presentation.model

sealed class UiState {
    data object Loading : UiState()
    data class Error(val message: String) : UiState()
    data object Empty : UiState()
    data object Success : UiState()
    data object Fail : UiState()
}
