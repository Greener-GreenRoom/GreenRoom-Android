package com.greener.presentation.ui.home.registraion.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.presentation.model.registration.PlantRegistrationInfo
import com.greener.presentation.util.MutableEventFlow
import com.greener.presentation.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationSearchViewModel @Inject constructor(): ViewModel() {
    val searchTerm = MutableStateFlow("")

    private val _plantRegistrationInfo = MutableStateFlow<PlantRegistrationInfo?>(null)
    val plantRegistrationInfo : StateFlow<PlantRegistrationInfo?> get() = _plantRegistrationInfo

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    init {
        // 임시 식물등록하기 info
        viewModelScope.launch{
            _plantRegistrationInfo.emit(PlantRegistrationInfo(plantId = null, lastWatering = null, nickname = null, waterDuration = null, shape = null))
        }
    }

    fun goToRegistrationNicknameImage() {
        viewModelScope.launch {
            val info = plantRegistrationInfo.value
            info?.let {
                _event.emit(Event.GoToRegistrationNicknameImage(it))
            }
        }
    }

    sealed class Event() {
        data class GoToRegistrationNicknameImage(
            val plantRegistrationInfo: PlantRegistrationInfo
        ) : Event()
    }
}
