package com.greener.presentation.ui.home.registration.nickname_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.domain.usecase.plant_register.IsDuplicateGreenRoomNicknameUseCase
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
    private val isDuplicateGreenRoomNicknameUseCase: IsDuplicateGreenRoomNicknameUseCase,
) : ViewModel() {

    val inputNickname = MutableStateFlow("")

    private val _stateOfNickname = MutableStateFlow<StateOfNickname>(StateOfNickname.Blank)
    val stateOfNickname: StateFlow<StateOfNickname> get() = _stateOfNickname

    private val _plantRegistrationInfo = MutableStateFlow<PlantRegistrationInfo?>(null)

    private val _plantImage = MutableStateFlow("")
    val plantImage: StateFlow<String> get() = _plantImage

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    fun initNavArgsData(plantRegistrationInfo: PlantRegistrationInfo) {
        viewModelScope.launch {
            _plantRegistrationInfo.emit(plantRegistrationInfo)
        }
    }

    fun checkGoodNickname(nickname: String) {
        viewModelScope.launch {
            var isDuplicate: Boolean = false

            // todo 식물등록하기 완료 후 api 연결 테스트 필요
            val result = isDuplicateGreenRoomNicknameUseCase(nickname)
            if (result.isSuccess) {
                isDuplicate = result.getOrThrow()
            } else {
                // todo 에러처리
            }

            if (isDuplicate) {
                _stateOfNickname.emit(StateOfNickname.Duplicate)
                return@launch
            }

            if (nickname.hasSpecialCharacters()) {
                _stateOfNickname.emit(StateOfNickname.SpecialChar)
                return@launch
            }

            if (nickname.length > 10) {
                _stateOfNickname.emit(StateOfNickname.TooLong)
                return@launch
            }

            if (nickname.isEmpty()) {
                _stateOfNickname.emit(StateOfNickname.Blank)
                return@launch
            }

            _stateOfNickname.emit(StateOfNickname.Good)
            updatePlantRegistrationInfo()
        }
    }

    private fun updatePlantRegistrationInfo() {
        viewModelScope.launch {
            val nickname = inputNickname.value
            val image = _plantImage.value.ifBlank { null }
            val newPlantRegistrationInfo = _plantRegistrationInfo.value

            _plantRegistrationInfo.emit(
                PlantRegistrationInfo(
                    plantId = newPlantRegistrationInfo?.plantId,
                    nickname = nickname,
                    lastWatering = newPlantRegistrationInfo?.lastWatering,
                    waterDuration = newPlantRegistrationInfo?.waterDuration,
                    shape = newPlantRegistrationInfo?.shape,
                    plantImage = image,
                ),
            )
        }
    }

    fun getImage(pickImageUseCase: PickImageUseCase) {
        viewModelScope.launch {
            val result = pickImageUseCase()
            if (result.isSuccess) {
                val image = result.getOrThrow()
                _plantImage.emit(image)
                updatePlantRegistrationInfo()
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
                updatePlantRegistrationInfo()
            } else {
                // todo 에러처리
            }
        }
    }

    fun deleteImage() {
        viewModelScope.launch {
            _plantImage.emit("")
        }
    }

    fun moveToWatering() {
        viewModelScope.launch {
            val info = _plantRegistrationInfo.value
            info?.let {
                _event.emit(Event.MoveToWatering(info))
            }
        }
    }

    fun showGetImageBottomSheet() {
        viewModelScope.launch {
            _event.emit(Event.ShowGetImageBottomSheet)
        }
    }

    private fun String.hasSpecialCharacters(): Boolean =
        Regex(regex).containsMatchIn(this)

    enum class StateOfNickname {
        Good, Duplicate, SpecialChar, TooLong, Blank
    }

    sealed class Event() {
        data class MoveToWatering(
            val plantRegistrationInfo: PlantRegistrationInfo,
        ) : Event()

        object ShowGetImageBottomSheet : Event()
    }

    companion object {
        const val regex = "[^A-Za-z0-9가-힣 ]"
    }
}
