package com.greener.presentation.ui.mypage.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.usecase.mypage.DeleteUserUserCase
import com.greener.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserWithdrawViewModel @Inject constructor(
    private val deleteUserUserCase: DeleteUserUserCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState

    fun deleteUser() {
        viewModelScope.launch {
            val result = deleteUserUserCase()
            if (result.isSuccess) {
                _uiState.update { UiState.Success }
            } else {
                _uiState.update { UiState.Error(result.exceptionOrNull()!!.message) }
                _uiState.update { UiState.Empty }
            }
        }
    }
}
