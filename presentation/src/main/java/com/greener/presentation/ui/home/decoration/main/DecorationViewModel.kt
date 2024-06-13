package com.greener.presentation.ui.home.decoration.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.domain.usecase.asset.GetAssetDetailTypeListUseCase
import com.greener.domain.usecase.asset.GetBackgroundAccessoryListUseCase
import com.greener.domain.usecase.asset.GetPlantAccessoryListUseCase
import com.greener.domain.usecase.asset.GetPlantShapeListUseCase
import com.greener.presentation.R
import com.greener.presentation.model.decoration.AllAssetViewItem
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.model.decoration.AssetViewItem
import com.greener.presentation.model.decoration.AssetViewObject
import com.greener.presentation.model.decoration.DecorationTabState
import com.greener.presentation.model.decoration.PlantDecorationDetailInfo
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.toAllBackgroundAccessoryAssetViewItem
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.toAllPlantAccessoryAssetViewItem
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.toAllPlantShapeAssetViewItem
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.toBackfroundAccessoryViewItem
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.toPlantAccessoryViewItem
import com.greener.presentation.ui.home.decoration.main.DecorationMappingObject.toPlantShapeAsserViewItem
import com.greener.presentation.util.MutableEventFlow
import com.greener.presentation.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DecorationViewModel @Inject constructor(
    private val getPlantShapeListUseCase: GetPlantShapeListUseCase,
    private val getPlantAccessoryListUseCase: GetPlantAccessoryListUseCase,
    private val getBackgroundAccessoryListUseCase: GetBackgroundAccessoryListUseCase,
    private val getAssetDetailTypeListUseCase: GetAssetDetailTypeListUseCase
) : ViewModel() {

    private val _assetDetailTypes = MutableStateFlow<List<AssetDetailTypeInfo>>(emptyList())
    val assetDetailTypes: StateFlow<List<AssetDetailTypeInfo>> get() = _assetDetailTypes

    private val _choiceAllViewAssets = MutableStateFlow<List<AllAssetViewItem>>(emptyList())
    val choiceAllViewAssets: StateFlow<List<AllAssetViewItem>> get() = _choiceAllViewAssets

    private val _choiceViewAssets = MutableStateFlow<List<AssetViewItem>>(emptyList())
    val choiceViewAssets: StateFlow<List<AssetViewItem>> get() = _choiceViewAssets

    private val _plantDecorationInfo = MutableStateFlow(PlantDecorationDetailInfo())
    val plantDecorationInfo: StateFlow<PlantDecorationDetailInfo> get() = _plantDecorationInfo

    private val _tabState = MutableStateFlow<DecorationTabState>(DecorationTabState.PLANT_DECORATION)
    val tabState: StateFlow<DecorationTabState> get() = _tabState

    private val _totalDecorationInfo = MutableStateFlow(DecorationInfo(null, null))
    val totalDecorationInfo: StateFlow<DecorationInfo> get() = _totalDecorationInfo

    init {
        viewModelScope.launch {
            // todo API 연결
            val plantShape = getPlantShapeListUseCase().firstOrNull()
            val plantAccessory1 =
                getPlantAccessoryListUseCase().filter { it.itemType == PlantAccessoryType.HEAD }.firstOrNull()
            val plantAccessory2 =
                getPlantAccessoryListUseCase().filter { it.itemType == PlantAccessoryType.EYE }.firstOrNull()
            val backgroundAccessory1 =
                getBackgroundAccessoryListUseCase().filter { it.itemType == BackgroundAccessoryType.BACK_LEFT }.firstOrNull()
            val backgroundAccessory2 =
                getBackgroundAccessoryListUseCase().filter { it.itemType == BackgroundAccessoryType.BACK_RIGHT }.firstOrNull()
            _plantDecorationInfo.emit(
                PlantDecorationDetailInfo(
                    shape = plantShape,
                    hairAccessory = plantAccessory1,
                    glasses = plantAccessory2,
                    backgroundWindow = backgroundAccessory1,
                    backgroundShelf = backgroundAccessory2
                )
            )
            onChangeAssetType(AssetType.PLANT_SHAPE)
        }
    }

    fun onChangeAssetType(assetType: AssetType) {
        viewModelScope.launch {
            val decorationInfo = plantDecorationInfo.value

            setAssetType(assetType)
            when (assetType) {
                AssetType.PLANT_SHAPE -> {
                    decorationInfo.shape?.let { updatePlantShapeAsset(null, it, true) }
                }

                AssetType.PLANT_ACCESSORY -> {
                    decorationInfo.glasses?.let { updatePlantAccessoryAsset(null, it, true) }
                }

                AssetType.BACKGROUND_ACCESSORY -> {
                    decorationInfo.backgroundWindow?.let { updateBackgroundAccessoryAsset(null, it, true) }
                }
            }
        }
    }

    private fun setAssetType(assetType: AssetType) {
        viewModelScope.launch {
            val typeList = emptyList<AssetDetailTypeInfo>().toMutableList()
            typeList.add(
                AssetDetailTypeInfo(
                    id = 0,
                    assetType = assetType,
                    type = ALL,
                    typeCode = R.string.all,
                    isChecked = true
                )
            )

            when (assetType) {
                AssetType.PLANT_SHAPE -> {
                    getAssetDetailTypeListUseCase(assetType).forEach { assetDetailType ->
                        typeList.add(assetDetailType)
                    }
                }

                AssetType.PLANT_ACCESSORY -> {
                    getAssetDetailTypeListUseCase(assetType).forEach {
                        if (it.type == PlantAccessoryType.FACE.name) {
                            return@forEach
                        }
                        typeList.add(it)
                    }
                }

                AssetType.BACKGROUND_ACCESSORY -> {
                    getAssetDetailTypeListUseCase(assetType).forEach {
                        typeList.add(it)
                    }
                }
            }
            _choiceViewAssets.emit(emptyList())
            _assetDetailTypes.emit(typeList)
        }
    }

    fun changeAssetDetailType(assetType: AssetType, targetType: Int) {
        viewModelScope.launch {
            val assetDetailTypeList = assetDetailTypes.value
            val targetDetailType = assetDetailTypeList.find { it.id == targetType }
            val plantDecorationInfo = plantDecorationInfo.value

            assetDetailTypeList.forEach {
                it.isChecked = it.id == targetType
            }
            _assetDetailTypes.emit(assetDetailTypeList)

            when (assetType) {
                AssetType.PLANT_SHAPE -> {
                    val plantShapeList = getPlantShapeListUseCase()
                    val isAll = assetDetailTypeList.find { it.id == targetType }?.type == ALL
                    val plantType = if (isAll) null else plantShapeList.find {
                        it.plantShapeType == PlantShapeType.valueOf(targetDetailType?.type ?: "")
                    }?.plantShapeType
                    plantDecorationInfo.shape?.let { plantShapeInfo ->
                        updatePlantShapeAsset(plantType, plantShapeInfo, isAll)
                    }
                }

                AssetType.PLANT_ACCESSORY -> {
                    val plantAccessoryList = getPlantAccessoryListUseCase()
                    val isALl = assetDetailTypeList.find { it.id == targetType }?.type == ALL
                    val accessoryType = if (isALl) null else plantAccessoryList.find {
                        it.itemType == PlantAccessoryType.valueOf(targetDetailType?.type ?: "")
                    }?.itemType
                    val targetIdCode = targetType.toString().first()
                    if (targetIdCode == EYE_ID) {
                        plantDecorationInfo.glasses?.let {
                            updatePlantAccessoryAsset(accessoryType, it, isALl)
                        }
                    } else {
                        plantDecorationInfo.hairAccessory?.let {
                            updatePlantAccessoryAsset(accessoryType, it, isALl)
                        }
                    }
                }

                AssetType.BACKGROUND_ACCESSORY -> {
                    val backgroundAccessoryList = getBackgroundAccessoryListUseCase()
                    val isAll = assetDetailTypeList.find { it.id == targetType }?.type == ALL
                    val accessoryType = if (isAll) null else backgroundAccessoryList.find {
                        it.itemType == BackgroundAccessoryType.valueOf(targetDetailType?.type ?: "")
                    }?.itemType
                    val targetIdCode = targetType.toString().first()
                    if (targetIdCode == WINDOW_ID) {
                        plantDecorationInfo.backgroundWindow?.let {
                            updateBackgroundAccessoryAsset(accessoryType, it, isAll)
                        }
                    } else {
                        plantDecorationInfo.backgroundShelf?.let {
                            updateBackgroundAccessoryAsset(accessoryType, it, isAll)
                        }
                    }
                }
            }
        }
    }

    fun updatePlantShapeAsset(
        plantType: PlantShapeType? = null,
        targetPlantShape: PlantShapeInfo,
        isAll: Boolean
    ) {
        viewModelScope.launch {
            val plantShapeList = getPlantShapeListUseCase()
            val plantInfo = plantDecorationInfo.value
            _plantDecorationInfo.emit(
                PlantDecorationDetailInfo(
                    shape = targetPlantShape,
                    glasses = plantInfo.glasses,
                    hairAccessory = plantInfo.hairAccessory,
                    backgroundShelf = plantInfo.backgroundShelf,
                    backgroundWindow = plantInfo.backgroundWindow
                ))

            _tabState.emit(DecorationTabState.PLANT_DECORATION)
            modifyDecorationInfo()

            if (isAll) {
                val itemAllList = emptyList<AllAssetViewItem>().toMutableList()

                getAssetDetailTypeListUseCase(AssetType.PLANT_SHAPE).forEach { type ->
                    itemAllList.add(
                        type.toAllPlantShapeAssetViewItem(targetPlantShape, plantShapeList)
                    )
                }
                _choiceAllViewAssets.emit(itemAllList)
                _choiceViewAssets.emit(emptyList())
            } else {
                val itemList = emptyList<AssetViewItem>().toMutableList()
                val plantShapeByType = plantShapeList.filter { it.plantShapeType == plantType }

                plantShapeByType.forEach { info ->
                        itemList.add(info.toPlantShapeAsserViewItem(targetPlantShape))
                    }
                _choiceViewAssets.emit(itemList)
                _choiceAllViewAssets.emit(emptyList())
            }
        }
    }

    fun updatePlantAccessoryAsset(
        accessoryType: PlantAccessoryType? = null,
        targetPlantAccessory: PlantAccessoryInfo,
        isAll: Boolean
    ) {
        viewModelScope.launch {
            if (accessoryType == PlantAccessoryType.FACE) return@launch

            val plantDecorationInfo = plantDecorationInfo.value
            var hairAccessory = plantDecorationInfo.hairAccessory
            var glasses = plantDecorationInfo.glasses

            if (accessoryType == PlantAccessoryType.EYE) {
                glasses = targetPlantAccessory
            } else if (accessoryType == PlantAccessoryType.HEAD) {
                hairAccessory = targetPlantAccessory
            }

            _tabState.emit(DecorationTabState.PLANT_DECORATION)

            _plantDecorationInfo.emit(
                PlantDecorationDetailInfo(
                    shape = plantDecorationInfo.shape,
                    glasses = glasses,
                    hairAccessory = hairAccessory,
                    backgroundShelf = plantDecorationInfo.backgroundShelf,
                    backgroundWindow = plantDecorationInfo.backgroundWindow
                ))
            modifyDecorationInfo()

            val plantAccessoryList = getPlantAccessoryListUseCase()
            if (isAll) {
                val itemAllList = emptyList<AllAssetViewItem>().toMutableList()
                getAssetDetailTypeListUseCase(AssetType.PLANT_ACCESSORY).forEach { type ->
                    if (type.type == PlantAccessoryType.FACE.name) {
                        return@forEach
                    }
                    itemAllList.add(
                        type.toAllPlantAccessoryAssetViewItem(hairAccessory!!, glasses!!, plantAccessoryList)
                    )
                }
                _choiceAllViewAssets.emit(itemAllList)
                _choiceViewAssets.emit(emptyList())
            } else {
                val itemList = emptyList<AssetViewItem>().toMutableList()
                val plantAccessoryByType = getPlantAccessoryListUseCase().filter { it.itemType == accessoryType }

                plantAccessoryByType.forEach { info ->
                    itemList.add(info.toPlantAccessoryViewItem(hairAccessory!!, glasses!!))
                }
                _choiceAllViewAssets.emit(emptyList())
                _choiceViewAssets.emit(itemList)
            }
        }
    }

    fun updateBackgroundAccessoryAsset(
        accessoryType: BackgroundAccessoryType? = null,
        targetPlantAccessory: BackgroundAccessoryInfo,
        isAll: Boolean
    ) {
        viewModelScope.launch {
            val plantDecorationInfo = plantDecorationInfo.value
            var shelfAccessory = plantDecorationInfo.backgroundShelf
            var windowAccessory = plantDecorationInfo.backgroundWindow

            if (accessoryType == BackgroundAccessoryType.BACK_LEFT) {
                windowAccessory = targetPlantAccessory
            } else if (accessoryType == BackgroundAccessoryType.BACK_RIGHT) {
                shelfAccessory = targetPlantAccessory
            }
            _plantDecorationInfo.emit(
                PlantDecorationDetailInfo(
                    shape = plantDecorationInfo.shape,
                    glasses = plantDecorationInfo.glasses,
                    hairAccessory = plantDecorationInfo.hairAccessory,
                    backgroundShelf = shelfAccessory,
                    backgroundWindow = windowAccessory
                ))

            _tabState.emit(DecorationTabState.BACKGROUND_DECORATION)
            modifyDecorationInfo()

            val backgroundAccessoryList = getBackgroundAccessoryListUseCase()

            if (isAll) {
                val itemAllList = emptyList<AllAssetViewItem>().toMutableList()
                getAssetDetailTypeListUseCase(AssetType.BACKGROUND_ACCESSORY).forEach { type ->
                    itemAllList.add(
                        type.toAllBackgroundAccessoryAssetViewItem(shelfAccessory!!, windowAccessory!!, backgroundAccessoryList)
                    )
                }
                _choiceAllViewAssets.emit(itemAllList)
                _choiceViewAssets.emit(emptyList())
            } else {
                val itemList = emptyList<AssetViewItem>().toMutableList()
                val backgroundAccessoryByType = getBackgroundAccessoryListUseCase().filter { it.itemType == accessoryType }

                backgroundAccessoryByType.forEach { info ->
                    itemList.add(info.toBackfroundAccessoryViewItem(shelfAccessory!!, windowAccessory!!))
                }
                _choiceAllViewAssets.emit(emptyList())
                _choiceViewAssets.emit(itemList)
            }
        }
    }

    fun saveDecorationInfo() {
        viewModelScope.launch {
            // todo Api 연결
        }
    }

    private fun modifyDecorationInfo() {
        viewModelScope.launch {
            val info = plantDecorationInfo.value
            val state = tabState.value

            _totalDecorationInfo.emit(DecorationInfo(info, state))
        }
    }

    data class DecorationInfo(
        val decorationDetailInfo: PlantDecorationDetailInfo?,
        val decorationState: DecorationTabState?
    )

    companion object {
        private const val ALL = "all"
        private const val EYE_ID = '2'
        private const val HAIR_ID = '3'
        private const val WINDOW_ID = '1'
        private const val SHELF_ID = '2'
    }
}
