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
    @Assisted private val greenRoom: GreenRoomTotalInfo,
) : ViewModel() {


    private val _myGreenRoom  = MutableStateFlow<GreenRoomTotalInfo?>(null)
    val myGreenRoom: StateFlow<GreenRoomTotalInfo?> get() = _myGreenRoom

    init {
        _myGreenRoom.value = greenRoom
    }

    @AssistedFactory
    interface GreenRoomViewModelFactory {
        fun create(greenRoom: GreenRoomTotalInfo): GreenRoomViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: GreenRoomViewModelFactory,
            greenRoom: GreenRoomTotalInfo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(greenRoom) as T
            }
        }
    }
}
