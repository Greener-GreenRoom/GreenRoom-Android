package com.greener.presentation.ui.home.registraion.nickname_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.presentation.model.registration.PlantRegistrationInfo
import com.greener.presentation.util.MutableEventFlow
import com.greener.presentation.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationNicknameImageViewModel @Inject constructor(
) : ViewModel() {

    val inputNickname = MutableStateFlow("")

    private val _plantRegistrationInfo = MutableStateFlow<PlantRegistrationInfo?>(null)
    val plantRegistrationInfo: StateFlow<PlantRegistrationInfo?> get() = _plantRegistrationInfo

    private val _plantImage = MutableStateFlow("")
    val plantImage : StateFlow<String> get() = _plantImage

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

    fun getImage(pickImageUseCase: PickImageUseCase) {
        viewModelScope.launch {
            val result = pickImageUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                _plantImage.emit(image)
            } else {
                // todo 에러 처리
            }
        }
    }

    fun takePicture(takePictureUseCase: TakePictureUseCase) {
        viewModelScope.launch {
            val result = takePictureUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                _plantImage.emit(image)
            } else {
                // todo 에러처리
            }
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
