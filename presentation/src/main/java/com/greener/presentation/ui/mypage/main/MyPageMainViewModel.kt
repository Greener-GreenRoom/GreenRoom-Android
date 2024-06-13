package com.greener.presentation.ui.mypage.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.mypage.Grade
import com.greener.domain.model.mypage.GradeTier
import com.greener.domain.model.mypage.SimpleProfile
import com.greener.domain.usecase.mypage.GetMyPageInfoUseCase
import com.greener.domain.usecase.mypage.LogoutUseCase
import com.greener.presentation.model.UiState
import com.greener.presentation.model.mypage.UserSimpleInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageMainViewModel @Inject constructor(
    val getMyPageInfoUseCase: GetMyPageInfoUseCase,
    val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _profile = MutableStateFlow<SimpleProfile?>(null)
    val profile: StateFlow<SimpleProfile?> get() = _profile

    private val _grade = MutableStateFlow<Grade?>(null)
    val grade: StateFlow<Grade?> get() = _grade

    private val _daysFromCreated = MutableStateFlow(0)
    val daysFromCreated: StateFlow<Int> get() = _daysFromCreated

    private val _myTier = MutableStateFlow<GradeTier?>(null)
    val myTier: StateFlow<GradeTier?> get() = _myTier

    private val _userSimpleInfo = MutableStateFlow<UserSimpleInfo?>(null)
    val userSimpleInfo: StateFlow<UserSimpleInfo?> get() = _userSimpleInfo

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun getMyPageInfo() {
        viewModelScope.launch {
            val result = getMyPageInfoUseCase()
            if (result.isSuccess) {
                Log.d("확인", result.toString())
                _grade.update { result.getOrNull()!!.gradeDto }
                _profile.update { result.getOrNull()!!.simpleProfile }
                _daysFromCreated.update { result.getOrNull()!!.daysFromCreated }
                _myTier.update { findMyTier(_grade.value!!.currentLevel) }
                _uiState.update { UiState.Success }
                _userSimpleInfo.update {
                    UserSimpleInfo(
                        profile.value!!.name,
                        findMyTier(_grade.value!!.currentLevel),
                        _profile.value!!.profileUrl
                    )
                }
            } else {
                _uiState.update { UiState.Error(result.exceptionOrNull()!!.message) }
            }

        }
    }

    private fun findMyTier(myLevel: Int): GradeTier {
        for (tier in GradeTier.entries) {
            if (tier.tierBegin <= myLevel && myLevel <= tier.tierEnd) {
                return tier
            }
        }
        return GradeTier.NONE
    }

}