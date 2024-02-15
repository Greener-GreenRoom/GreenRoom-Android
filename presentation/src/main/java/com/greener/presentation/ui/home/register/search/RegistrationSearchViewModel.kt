package com.greener.presentation.ui.home.register.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationSearchViewModel @Inject constructor(): ViewModel() {
    val searchTerm = MutableStateFlow("")
}
