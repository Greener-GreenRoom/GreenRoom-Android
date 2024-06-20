package com.greener.presentation.ui.home.registration.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.greener.domain.usecase.plant_register.GetPlantWateringTipUseCase
import com.greener.presentation.model.registration.PlantRegistrationInfo
import com.greener.presentation.util.MutableEventFlow
import com.greener.presentation.util.asEventFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class RegistrationWaterViewModel @AssistedInject constructor(
    private val getPlantWateringTipUseCase: GetPlantWateringTipUseCase,
    @Assisted private val plantRegistrationInfo: PlantRegistrationInfo
) : ViewModel() {

    @AssistedFactory
    interface PlantRegistrationInfoFactory {
        fun create(plantRegistrationInfo: PlantRegistrationInfo): RegistrationWaterViewModel
    }

    private val _plantWateringTip = MutableStateFlow("")
    val plantWateringTip : StateFlow<String> get() = _plantWateringTip

    private val _lastWatering = MutableStateFlow("")
    val lastWatering : StateFlow<String> get() = _lastWatering

    private val _viewLastWatering = MutableStateFlow("")
    val viewLastWatering : StateFlow<String> get() = _viewLastWatering

    private val _waterDuration = MutableStateFlow(NO_PICK_DURATION)
    val waterDuration : StateFlow<Int> get() = _waterDuration

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    init {
        initWateringTip()
        checkStateOfInput()
    }

    private fun initWateringTip() {
        viewModelScope.launch {
            val result = getPlantWateringTipUseCase(plantRegistrationInfo.plantId ?: 0L)

            if (result.isSuccess) {
                _plantWateringTip.emit(result.getOrThrow())
            } else {
                // todo 에러 처리
            }

        }
    }

    fun onUpdateSelectDate(resultTime: Long) {
        viewModelScope.launch {
            val infoDateFormat = SimpleDateFormat(INFO_DATE_FORMAT)
            val viewDateFormat = SimpleDateFormat(VIEW_DATE_FORMAT)

            val infoFormatTime = infoDateFormat.format(resultTime)
            val viewFormatTime = viewDateFormat.format(resultTime)

            _lastWatering.emit(infoFormatTime)
            _viewLastWatering.emit(viewFormatTime)

            checkStateOfInput()
        }
    }

    fun onUpdateDuration(duration: String) {
        viewModelScope.launch {
            val waterDurationInt = duration.toInt()
            if (waterDurationInt == NO_PICK_DURATION) {
                return@launch
            } else if (waterDurationInt > MAX_DURATION) {
                _event.emit(Event.InputMaxDuration)
            } else {
                _waterDuration.emit(waterDurationInt)
            }
        }
    }

    fun showDatePicker() {
        viewModelScope.launch {
            _event.emit(Event.ShowDatePicker)
        }
    }

    fun moveToPlantShape() {
        viewModelScope.launch {
            val lastWateringDate = lastWatering.value
            val duration = waterDuration.value

            if (lastWateringDate.isEmpty()) {
                return@launch
            }

            if (duration == NO_PICK_DURATION) {
                return@launch
            }

            val newInfo = PlantRegistrationInfo(
                plantId = plantRegistrationInfo.plantId,
                nickname = plantRegistrationInfo.nickname,
                lastWatering = lastWateringDate,
                waterDuration = duration,
                shape = plantRegistrationInfo.shape,
                plantImage = plantRegistrationInfo.plantImage,
            )

            _event.emit(Event.MoveToPlantShape(newInfo))
        }
    }

    private fun checkStateOfInput() {
        viewModelScope.launch {
            val lastWateringDate = lastWatering.value
            val duration = waterDuration.value

            if (lastWateringDate.isEmpty().not() && duration != NO_PICK_DURATION) {
                _event.emit(Event.AllInputState)
            } else {
                _event.emit(Event.PartialInputState)
            }
        }
    }

    sealed class Event() {
        data class MoveToPlantShape(
            val plantRegistrationInfo: PlantRegistrationInfo
        ): Event()

        data object ShowDatePicker : Event()
        data object InputMaxDuration : Event()
        data object AllInputState: Event()
        data object PartialInputState: Event()
    }

    companion object {
        fun provideFactory(
            assisted: PlantRegistrationInfoFactory,
            plantRegistrationInfo: PlantRegistrationInfo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assisted.create(plantRegistrationInfo) as T
            }
        }

        private const val INFO_DATE_FORMAT = "yyyy-MM-dd"
        private const val VIEW_DATE_FORMAT = "yyyy/MM/dd"
        private const val NO_PICK_DURATION = 0
        private const val MAX_DURATION = 364
    }
}
