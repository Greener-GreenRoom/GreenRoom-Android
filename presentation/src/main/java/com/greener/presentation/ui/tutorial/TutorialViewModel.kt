package com.greener.presentation.ui.tutorial

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TutorialViewModel : ViewModel() {

    private val _step = MutableStateFlow(1)
    val step: StateFlow<Int> get() = _step

    fun nextStep() {
        _step.value++
    }
}