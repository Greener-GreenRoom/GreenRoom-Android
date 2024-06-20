package com.greener.presentation.ui.home.registration.plant_shape

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.usecase.asset.GetAssetDetailTypeListUseCase
import com.greener.domain.usecase.asset.GetPlantShapeListUseCase
import com.greener.presentation.R
import com.greener.presentation.model.registration.PlantRegistrationInfo
import com.greener.presentation.ui.home.decoration.main.DecorationViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationPlantShapeViewModel @AssistedInject constructor(
    private val getAssetDetailTypeListUseCase: GetAssetDetailTypeListUseCase,
    private val getPlantShapeListUseCase: GetPlantShapeListUseCase,
    @Assisted private val plantRegistrationInfo: PlantRegistrationInfo
) : ViewModel() {

    @AssistedFactory
    interface PlantRegistrationInfoFactory {
        fun create(plantRegistrationInfo: PlantRegistrationInfo): RegistrationPlantShapeViewModel
    }

    private val _shapeDetailTypes = MutableStateFlow<List<AssetDetailTypeInfo>>(emptyList())
    val shapeDetailTypes: StateFlow<List<AssetDetailTypeInfo>> get() = _shapeDetailTypes

    private val _plantShapes = MutableStateFlow<List<PlantShapeInfo>>(emptyList())
    val plantShape: StateFlow<List<PlantShapeInfo>> get() = _plantShapes

    private val _allPlantShapes = MutableStateFlow<List<PlantShapeInfo>>(emptyList())
    val allPlantShape: StateFlow<List<PlantShapeInfo>> get() = _allPlantShapes

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            val detailTypes = getAssetDetailTypeListUseCase(AssetType.PLANT_SHAPE)
            val totalDetailTypes = emptyList<AssetDetailTypeInfo>().toMutableList()
            totalDetailTypes.add(
                AssetDetailTypeInfo(
                    id = 0,
                    assetType = AssetType.PLANT_SHAPE,
                    type = ALL,
                    typeCode = R.string.all,
                    isChecked = true
                )
            )
            detailTypes.forEach { type ->
                totalDetailTypes.add(type)
            }
            _shapeDetailTypes.emit(totalDetailTypes)

            val plantShapes = getPlantShapeListUseCase()
            _allPlantShapes.emit(plantShapes)
        }
    }

    fun onChangeType(targetId: Int) {
        viewModelScope.launch {
            val shapeDetailList = shapeDetailTypes.value
            shapeDetailList.forEach {
                it.isChecked = it.id == targetId
            }
            _shapeDetailTypes.emit(shapeDetailList)
        }
    }


    companion object {
        fun provideFactory(
            assisted: PlantRegistrationInfoFactory,
            plantRegistrationInfo: PlantRegistrationInfo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return assisted.create(plantRegistrationInfo) as T
            }
        }

        private const val ALL = "all"
    }
}
