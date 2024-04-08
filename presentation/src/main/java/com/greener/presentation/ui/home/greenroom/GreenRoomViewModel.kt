package com.greener.presentation.ui.home.greenroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greener.domain.model.ExampleModel
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GreenRoomViewModel @AssistedInject constructor(
    @Assisted private val plant: ExampleModel,
) : ViewModel() {
    private val _myPlant = MutableStateFlow<ExampleModel?>(null)
    val myPlant: MutableStateFlow<ExampleModel?> get() = _myPlant

    private val _myGreenRoom  = MutableStateFlow<GreenRoomTotalInfo?>(null)
    val myGreenRoom: StateFlow<GreenRoomTotalInfo?> get() = _myGreenRoom

    init {
        _myPlant.value = plant
    }

    @AssistedFactory
    interface GreenRoomViewModelFactory {
        fun create(plant: ExampleModel): GreenRoomViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: GreenRoomViewModelFactory,
            plant: ExampleModel,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(plant) as T
            }
        }
    }
}
