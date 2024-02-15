package com.greener.presentation.ui.home.register.nickname_image

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationNicknameImageViewModel @Inject constructor(): ViewModel() {

    val inputNickname = MutableStateFlow("")
}
