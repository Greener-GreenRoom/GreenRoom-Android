package com.greener.presentation.ui.home.registration.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.plant_register.PlantInformationData
import com.greener.domain.usecase.plant_register.GetPlantInformationUseCase
import com.greener.presentation.model.registration.PlantRegistrationInfo
import com.greener.presentation.util.MutableEventFlow
import com.greener.presentation.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationSearchViewModel @Inject constructor(
    val getPlantInformationUseCase: GetPlantInformationUseCase,
) : ViewModel() {
    val searchTerm = MutableStateFlow("")

    private val _searchPlantInfo = MutableStateFlow<List<PlantInformationData>>(emptyList())
    val searchPlantInfo: StateFlow<List<PlantInformationData>> get() = _searchPlantInfo

    private val _allPlantInfo = MutableStateFlow<List<PlantInformationData>>(emptyList())

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    init {
        initAllPlantInformation()
    }

    fun searchPlantInfo() {
        viewModelScope.launch {
            val search = searchTerm.value
            val allInfo = _allPlantInfo.value

            val searchPlant = allInfo.filter { it.distributionName.contains(search) }
            _searchPlantInfo.emit(searchPlant)
        }
    }
    fun getPopularPlantInfo() {
        viewModelScope.launch {
            val result = getPlantInformationUseCase(null, 10)
            if (result.isSuccess) {
                _searchPlantInfo.emit(result.getOrThrow())
            }
        }
    }

    private fun initAllPlantInformation() {
        getPopularPlantInfo()
        viewModelScope.launch {
            val result = getPlantInformationUseCase(null, null)
            if (result.isSuccess) {
                _allPlantInfo.emit(result.getOrThrow())
            } else {
                // todo 에러처리
            }
        }
    }

    fun goToRegistrationNicknameImage(plantId: Long?) {
        viewModelScope.launch {
            val plantRegistrationInfo = PlantRegistrationInfo(plantId, null, null, null, null)
            _event.emit(Event.GoToRegistrationNicknameImage(plantRegistrationInfo))
        }
    }

    sealed class Event() {
        data class GoToRegistrationNicknameImage(
            val plantRegistrationInfo: PlantRegistrationInfo,
        ) : Event()
    }
}
