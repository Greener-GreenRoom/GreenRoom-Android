package com.greener.presentation.ui.home.registration.plant_shape

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.PlantShape
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.domain.model.plant_register.PlantRegisterRequestData
import com.greener.domain.usecase.asset.GetAssetDetailTypeListUseCase
import com.greener.domain.usecase.asset.GetPlantShapeListUseCase
import com.greener.domain.usecase.plant_register.RegisterGreenRoomUseCase
import com.greener.presentation.R
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.model.registration.PlantRegistrationInfo
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.toAllPlantShapeAssetViewItem
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.updateChecked
import com.greener.presentation.util.MutableEventFlow
import com.greener.presentation.util.asEventFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationPlantShapeViewModel @AssistedInject constructor(
    private val getAssetDetailTypeListUseCase: GetAssetDetailTypeListUseCase,
    private val getPlantShapeListUseCase: GetPlantShapeListUseCase,
    private val registerGreenRoomUseCase: RegisterGreenRoomUseCase,
    @Assisted private val plantRegistrationInfo: PlantRegistrationInfo,
) : ViewModel() {

    @AssistedFactory
    interface PlantRegistrationInfoFactory {
        fun create(plantRegistrationInfo: PlantRegistrationInfo): RegistrationPlantShapeViewModel
    }

    private val _shapeDetailTypes = MutableStateFlow<List<AssetDetailTypeInfo>>(emptyList())
    val shapeDetailTypes: StateFlow<List<AssetDetailTypeInfo>> get() = _shapeDetailTypes

    private val _plantShapes = MutableStateFlow<List<PlantShapeInfo>>(emptyList())
    val plantShape: StateFlow<List<PlantShapeInfo>> get() = _plantShapes

    private val _allPlantShapes = MutableStateFlow<List<AllAssetViewObject.AllPlantShapeObject>>(emptyList())
    val allPlantShape: StateFlow<List<AllAssetViewObject.AllPlantShapeObject>> get() = _allPlantShapes

    private val _choicePlantShape = MutableStateFlow<PlantShapeInfo?>(null)
    val choicePlantShape: StateFlow<PlantShapeInfo?> get() = _choicePlantShape

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

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
                    isChecked = true,
                ),
            )
            detailTypes.forEach { type ->
                totalDetailTypes.add(type)
            }
            _shapeDetailTypes.emit(totalDetailTypes)

            val defaultShape = getPlantShapeListUseCase().find { it.plantShape == PlantShape.Main_Character }
            defaultShape?.let {
                _choicePlantShape.emit(defaultShape)
                updatePlantShapeAsset(null, it, true)
            }
        }
    }

    fun onChangeType(targetId: Int) {
        viewModelScope.launch {
            val shapeDetailList = shapeDetailTypes.value
            val targetDetailType = shapeDetailList.find { it.id == targetId }
            val targetPlantShape = choicePlantShape.value
            shapeDetailList.forEach {
                it.isChecked = it.id == targetId
            }
            _shapeDetailTypes.emit(shapeDetailList)

            val plantShapeList = getPlantShapeListUseCase()
            val isAll = shapeDetailList.find { it.id == targetId }?.type == ALL
            val plantType = if (isAll) {
                null
            } else {
                plantShapeList.find {
                    it.plantShapeType == PlantShapeType.valueOf(targetDetailType?.type ?: "")
                }?.plantShapeType
            }

            targetPlantShape?.let {
                updatePlantShapeAsset(plantType, it, isAll)
            }
        }
    }

    fun updatePlantShapeAsset(
        plantType: PlantShapeType? = null,
        targetPlantShape: PlantShapeInfo,
        isAll: Boolean,
    ) {
        viewModelScope.launch {
            val plantShapeList = getPlantShapeListUseCase()
            val detailTypes = getAssetDetailTypeListUseCase(AssetType.PLANT_SHAPE)
            _choicePlantShape.emit(targetPlantShape)

            if (isAll) {
                val shapeAllList = emptyList<AllAssetViewObject.AllPlantShapeObject>().toMutableList()
                detailTypes.forEach { type ->
                    shapeAllList.add(type.toAllPlantShapeAssetViewItem(targetPlantShape, plantShapeList).viewObject as AllAssetViewObject.AllPlantShapeObject)
                }
                _allPlantShapes.emit(shapeAllList)
                _plantShapes.emit(emptyList())
            } else {
                val shapeList = emptyList<PlantShapeInfo>().toMutableList()
                val plantShapeByType = plantShapeList.filter { it.plantShapeType == plantType }
                plantShapeByType.forEach { info ->
                    shapeList.add(info.updateChecked(targetPlantShape.id))
                }
                _allPlantShapes.emit(emptyList())
                _plantShapes.emit(shapeList)
            }
        }
    }

    fun completePlantRegistration() {
        viewModelScope.launch {
            val plantShape = choicePlantShape.value
            val plantRegisterRequestData = PlantRegisterRequestData(
                plantId = plantRegistrationInfo.plantId,
                name = plantRegistrationInfo.nickname!!,
                lastWatering = plantRegistrationInfo.lastWatering!!,
                wateringDuration = plantRegistrationInfo.waterDuration!!,
                shape = plantShape!!.plantShape.nameString
            )

            val result = registerGreenRoomUseCase(plantRegisterRequestData, plantRegistrationInfo.plantImage)
            if (result.isSuccess) {
                // todo 다음 화면
            } else {
                // todo api 연결
            }
        }
    }

    sealed class Event() {
        data class MoveToComplete(
            val plantRegistrationInfo: PlantRegistrationInfo,
        ) : Event()
    }

    companion object {
        fun provideFactory(
            assisted: PlantRegistrationInfoFactory,
            plantRegistrationInfo: PlantRegistrationInfo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return assisted.create(plantRegistrationInfo) as T
            }
        }

        private const val ALL = "all"
    }
}
