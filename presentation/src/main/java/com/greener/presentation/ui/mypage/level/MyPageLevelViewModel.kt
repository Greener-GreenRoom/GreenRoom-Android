package com.greener.presentation.ui.mypage.level

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.mypage.GradeTier
import com.greener.domain.model.mypage.MyLevelInfo
import com.greener.domain.usecase.mypage.GetMyLevelInfoUseCase
import com.greener.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageLevelViewModel @Inject constructor(
    private val getMyLevelInfoUseCase: GetMyLevelInfoUseCase,
) : ViewModel() {
    init {
        getMyLevelInfo()
    }

    private val _myLevelInfo = MutableStateFlow<MyLevelInfo?>(null)
    val myLevelInfo: StateFlow<MyLevelInfo?> get() = _myLevelInfo

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState

    private fun getMyLevelInfo() {
        viewModelScope.launch {
            val result = getMyLevelInfoUseCase()
            if (result.isSuccess) {
                _myLevelInfo.update { result.getOrNull()!! }
                _uiState.update { UiState.Success }
            } else {
                _uiState.update { UiState.Error(result.exceptionOrNull()!!.message) }
            }
        }
    }

    fun getMyLevel(): Int? {
        return _myLevelInfo.value?.grade?.currentLevel
    }

    fun getCurrentSeed(): Int? {
        return _myLevelInfo.value?.grade?.currentSeed
    }

    fun getNextLevelSeed(): Int? {
        return _myLevelInfo.value?.grade?.nextLevelSeed
    }

    fun getNextLevelToGetItems(): Int? {
        return _myLevelInfo.value?.nextLevelToGetItems
    }

    fun getMyTier(): GradeTier? {
        return _myLevelInfo.value?.myTier
    }

    fun getProgress(): Int {
        if (_myLevelInfo.value == null) {
            return 0
        }
        return (myLevelInfo.value!!.grade.currentSeed * PERCENT_UNIT / _myLevelInfo.value!!.grade.nextLevelSeed)
    }
    companion object {
        const val PERCENT_UNIT = 100
    }
}
