package com.greener.presentation.ui.home.registration.complete

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.asset.PlantShape
import com.greener.domain.usecase.asset.GetPlantShapeListUseCase
import com.greener.presentation.model.registration.PlantRegistrationInfo
import com.greener.presentation.util.MutableEventFlow
import com.greener.presentation.util.asEventFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class RegistrationCompleteViewModel @AssistedInject constructor(
    private val getPlantShapeListUseCase: GetPlantShapeListUseCase,
    @Assisted private val plantRegistrationInfo: PlantRegistrationInfo,
) : ViewModel() {

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    @AssistedFactory
    interface PlantRegistrationInfoFactory {
        fun create(plantRegistrationInfo: PlantRegistrationInfo): RegistrationCompleteViewModel
    }

    init {
        viewModelScope.launch {
            val plantList = getPlantShapeListUseCase()
            val myPlantInfo = plantList.find { it.plantShape == PlantShape.valueOf(plantRegistrationInfo.shape ?: "") }

            myPlantInfo?.let {
                _event.emit(Event.PrintMyPlantShape(it.drawableID))
            }
        }
    }

    fun moveToHome() {
        viewModelScope.launch {
            _event.emit(Event.MoveToHome)
        }
    }

    sealed class Event() {
        data class PrintMyPlantShape(
            val plantShapeDrawableID: Int,
        ) : Event()

        data object MoveToHome : Event()
    }

    companion object {
        fun provideFactory(
            assisted: PlantRegistrationInfoFactory,
            plantRegistrationInfo: PlantRegistrationInfo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assisted.create(plantRegistrationInfo) as T
            }
        }
    }
}
