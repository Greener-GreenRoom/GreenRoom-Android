package com.greener.presentation.ui.home.decoration.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.AssetType
import com.greener.domain.model.asset.BackgroundAccessory
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessory
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.domain.model.home.PlantDecorationInfo
import com.greener.domain.usecase.asset.GetAssetDetailTypeListUseCase
import com.greener.domain.usecase.asset.GetBackgroundAccessoryListUseCase
import com.greener.domain.usecase.asset.GetPlantAccessoryListUseCase
import com.greener.domain.usecase.asset.GetPlantShapeListUseCase
import com.greener.presentation.R
import com.greener.presentation.model.decoration.AllAssetViewItem
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.model.decoration.AssetViewItem
import com.greener.presentation.model.decoration.AssetViewObject
import com.greener.presentation.model.decoration.PlantDecorationIdInfo
import com.greener.presentation.ui.home.decoration.main.adapter.DecorationMappingObject.toAllPlantShapeAssetViewItem
import com.greener.presentation.ui.home.decoration.main.adapter.DecorationMappingObject.toPlantShapeAsserViewItem
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

    private val _plantDecorationIdInfo = MutableStateFlow<PlantDecorationIdInfo>(PlantDecorationIdInfo())
    val plantDecorationIdInfo : StateFlow<PlantDecorationIdInfo> get() = _plantDecorationIdInfo

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    init {
        onChangeAssetType(AssetType.PLANT_SHAPE)
    }

    fun onChangeAssetType(assetType: AssetType) {
        setAssetDetailType(assetType)
        setAllAssetView(assetType)
    }

    private fun setAssetDetailType(assetType: AssetType) {
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

    fun changeAssetDetailTypeCheck(targetId: Int) {
        viewModelScope.launch {
            val assetDetailTypeList = assetDetailTypes.value
            val targetDetailType = assetDetailTypeList.find { it.id == targetId }
            assetDetailTypeList.forEach {
                it.isChecked = it.id == targetId
            }

            if (assetDetailTypeList.find { it.id == targetId }?.type != ALL) {
                _choiceAllViewAssets.emit(emptyList())
                setAssetItem(targetDetailType)
            } else {
                _choiceViewAssets.emit(emptyList())
                setAllAssetView(targetDetailType!!.assetType)
            }
            _assetDetailTypes.emit(assetDetailTypeList)
        }
    }

    fun updatePlantShapeAsset(targetPlantShape: PlantShapeInfo, isAll: Boolean) {
        viewModelScope.launch {
            val itemAllList = emptyList<AllAssetViewItem>().toMutableList()

            val plantShapeList = getPlantShapeListUseCase()
            getAssetDetailTypeListUseCase(AssetType.PLANT_SHAPE).forEach { type ->
                itemAllList.add(
                    type.toAllPlantShapeAssetViewItem(targetPlantShape, plantShapeList)
                )
            }

            val itemList = emptyList<AssetViewItem>().toMutableList()

            val plantShapeType = plantShapeList.find { it == targetPlantShape }?.plantShapeType ?: PlantShapeType.ANNUAL
            plantShapeList.filter { it.plantShapeType.name == plantShapeType.name }.forEach { info ->
                itemList.add(
                    info.toPlantShapeAsserViewItem(targetPlantShape)
                )
            }

            val plantDecoration = plantDecorationIdInfo.value
            val newPlantDecoration = PlantDecorationIdInfo(
                shape = targetPlantShape.drawableID,
                glasses = plantDecoration.glasses,
                hairAccessory = plantDecoration.hairAccessory,
                backgroundWindow = plantDecoration.backgroundWindow,
                backgroundShelf = plantDecoration.backgroundShelf
            )
            _plantDecorationIdInfo.emit(newPlantDecoration)
            if (isAll) _choiceAllViewAssets.emit(itemAllList) else _choiceViewAssets.emit(itemList)
        }
    }

//    fun updatePlantAccessoryAsset(targetEyeId: Int?, targetHeadId: Int?) {
//        viewModelScope.launch {
//            if (targetEyeId.toString().first() == targetHeadId.toString().first()) {
//                if ((targetEyeId == null && targetHeadId == null).not()) {
//                    _event.emit(Event.TwoAccessoryInSameType)
//                    return@launch
//                }
//            }
//
//            val itemAllList = emptyList<AllAssetViewItem>().toMutableList()
//
//            val plantAccessoryList = getPlantAccessoryListUseCase()
//            getAssetDetailTypeListUseCase(AssetType.PLANT_ACCESSORY).forEach { type ->
//                if (type.type == PlantAccessoryType.FACE.name) {
//                    return@forEach
//                }
//                itemAllList.add(
//                    AllAssetViewItem(
//                        AssetType.PLANT_ACCESSORY,
//                        AllAssetViewObject.AllPlantAccessoriesObject(
//                            plantAccessoryType = PlantAccessoryType.valueOf(type.type),
//                            plantAccessoryTypeCode = type.typeCode,
//                            infoList = plantAccessoryList.filter { it.itemType.name == type.type }
//                                .map { info ->
//                                    PlantAccessoryInfo(
//                                        id = info.id,
//                                        itemType = info.itemType,
//                                        plantAccessory = info.plantAccessory,
//                                        limitLevel = info.limitLevel,
//                                        drawableID = info.drawableID,
//                                        (targetEyeId == info.id) || (targetHeadId == info.id)
//                                    )
//                                }
//                        )
//                    )
//                )
//            }
//
//
//            val itemList = emptyList<AssetViewItem>().toMutableList()
//
//            val plantAccessory = getPlantAccessoryListUseCase()
//            plantAccessory.filter { it.itemType.name == detailType.type }.forEach { info ->
//                itemList.add(
//                    AssetViewItem(
//                        assetType = AssetType.PLANT_ACCESSORY,
//                        viewObject = AssetViewObject.PlantAccessoriesObject(info)
//                    )
//                )
//            }
//
//        }
//    }

    fun changeBackgroundAccessoryAsset(targetShelfId: Int?, targetWindowId: Int?) {
        viewModelScope.launch { }
    }

    private fun setAllAssetView(assetType: AssetType) {
        viewModelScope.launch {
            val itemList = emptyList<AllAssetViewItem>().toMutableList()

            when (assetType) {
                AssetType.PLANT_SHAPE -> {
                    val plantShapeList = getPlantShapeListUseCase()
                    getAssetDetailTypeListUseCase(assetType).forEach { type ->
                        itemList.add(
                            AllAssetViewItem(
                                assetType,
                                AllAssetViewObject.AllPlantShapeObject(
                                    plantShapeType = PlantShapeType.valueOf(type.type),
                                    plantShapeTypeCode = type.typeCode,
                                    infoList = plantShapeList.filter { it.plantShapeType.name == type.type }
                                )
                            )
                        )
                    }
                }

                AssetType.PLANT_ACCESSORY -> {
                    val plantAccessoryList = getPlantAccessoryListUseCase()
                    getAssetDetailTypeListUseCase(assetType).forEach { type ->
                        if (type.type == PlantAccessoryType.FACE.name) {
                            return@forEach
                        }
                        itemList.add(
                            AllAssetViewItem(
                                assetType,
                                AllAssetViewObject.AllPlantAccessoriesObject(
                                    plantAccessoryType = PlantAccessoryType.valueOf(type.type),
                                    plantAccessoryTypeCode = type.typeCode,
                                    infoList = plantAccessoryList.filter { it.itemType.name == type.type }
                                )
                            )
                        )
                    }
                }

                AssetType.BACKGROUND_ACCESSORY -> {
                    val backgroundAccessoryList = getBackgroundAccessoryListUseCase()
                    getAssetDetailTypeListUseCase(assetType).forEach { type ->
                        itemList.add(
                            AllAssetViewItem(
                                assetType,
                                AllAssetViewObject.AllBackgroundAccessoriesObject(
                                    backgroundAccessorType = BackgroundAccessoryType.valueOf(type.type),
                                    backgroundAccessoryTypeCode = type.typeCode,
                                    infoList = backgroundAccessoryList.filter { it.itemType.name == type.type }
                                )
                            )
                        )
                    }
                }
            }
            _choiceAllViewAssets.emit(itemList)
        }
    }

    private fun setAssetItem(detailType: AssetDetailTypeInfo?) {
        viewModelScope.launch {
            val itemList = emptyList<AssetViewItem>().toMutableList()

            when (detailType!!.assetType) {
                AssetType.PLANT_SHAPE -> {
                    val plantShape = getPlantShapeListUseCase()
                    plantShape.filter { it.plantShapeType.name == detailType.type }
                        .forEach { info ->
                            itemList.add(
                                AssetViewItem(
                                    assetType = AssetType.PLANT_SHAPE,
                                    viewObject = AssetViewObject.PlantShapeObject(info)
                                )
                            )
                        }
                }

                AssetType.PLANT_ACCESSORY -> {
                    val plantAccessory = getPlantAccessoryListUseCase()
                    plantAccessory.filter { it.itemType.name == detailType.type }.forEach { info ->
                        itemList.add(
                            AssetViewItem(
                                assetType = AssetType.PLANT_ACCESSORY,
                                viewObject = AssetViewObject.PlantAccessoriesObject(info)
                            )
                        )
                    }
                }

                AssetType.BACKGROUND_ACCESSORY -> {
                    val backgroundAccessory = getBackgroundAccessoryListUseCase()
                    backgroundAccessory.filter { it.itemType.name == detailType.type }
                        .forEach { info ->
                            itemList.add(
                                AssetViewItem(
                                    assetType = AssetType.BACKGROUND_ACCESSORY,
                                    viewObject = AssetViewObject.BackgroundAccessoriesObject(info)
                                )
                            )
                        }
                }
            }
            _choiceViewAssets.emit(itemList)
        }
    }

    fun updatePlantDecoration(newPlantDecorationIdInfo: PlantDecorationIdInfo) {
        viewModelScope.launch {
            // todo preview 전환
            _plantDecorationIdInfo.emit(newPlantDecorationIdInfo)
        }
    }

    sealed class Event() {
        object TwoAccessoryInSameType : Event()
    }

    companion object {
        private const val ALL = "all"
    }
}
