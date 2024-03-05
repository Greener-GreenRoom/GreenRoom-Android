package com.greener.presentation.ui.home.registraion.nickname_image

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
class RegistrationNicknameImageViewModel @Inject constructor() : ViewModel() {

    val inputNickname = MutableStateFlow("")

    private val _plantRegistrationInfo = MutableStateFlow<PlantRegistrationInfo?>(null)
    val plantRegistrationInfo: StateFlow<PlantRegistrationInfo?> get() = _plantRegistrationInfo

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    fun initNavArgsData(plantRegistrationInfo: PlantRegistrationInfo) {
        viewModelScope.launch {
            _plantRegistrationInfo.emit(plantRegistrationInfo)
        }
    }

    fun inputPlantNickname() {
        viewModelScope.launch {
            val nickname = inputNickname.value
            val newPlantRegistrationInfo = plantRegistrationInfo.value
            _plantRegistrationInfo.emit(
                PlantRegistrationInfo(
                    plantId = newPlantRegistrationInfo?.plantId,
                    nickname = nickname,
                    lastWatering = newPlantRegistrationInfo?.lastWatering,
                    waterDuration = newPlantRegistrationInfo?.waterDuration,
                    shape = newPlantRegistrationInfo?.shape
                )
            )
        }
    }

    fun inputPlantImage() {
        viewModelScope.launch {
            // todo
        }
    }

    fun moveToWatering() {
        viewModelScope.launch {
            val info = plantRegistrationInfo.value
            _event.emit(Event.MoveToWatering(info!!))
        }
    }

    sealed class Event() {
        data class MoveToWatering(
            val plantRegistrationInfo: PlantRegistrationInfo
        ): Event()
    }
}
