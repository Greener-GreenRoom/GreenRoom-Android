package com.greener.presentation.ui.home.decoration.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.asset.AssetDetailTypeInfo
import com.greener.domain.model.asset.BackgroundAccessoryInfo
import com.greener.domain.model.asset.BackgroundAccessoryType
import com.greener.domain.model.asset.PlantAccessoryInfo
import com.greener.domain.model.asset.PlantAccessoryType
import com.greener.domain.model.asset.PlantShapeInfo
import com.greener.domain.model.asset.PlantShapeType
import com.greener.domain.usecase.asset.GetBackgroundAccessoryListUseCase
import com.greener.domain.usecase.asset.GetPlantAccessoryListUseCase
import com.greener.domain.usecase.asset.GetPlantShapeListUseCase
import com.greener.presentation.R
import com.greener.domain.model.asset.AssetType
import com.greener.domain.usecase.asset.GetAssetDetailTypeListUseCase
import com.greener.presentation.model.decoration.AllAssetViewItem
import com.greener.presentation.model.decoration.AllAssetViewObject
import com.greener.presentation.model.decoration.AssetViewItem
import com.greener.presentation.model.decoration.AssetViewObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
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

    init {
        setAssetDetailType(AssetType.PLANT_SHAPE)
        setAllAssetView(AssetType.PLANT_SHAPE)
    }

    fun onChangeAsseType(assetType: AssetType) {
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
                    plantShape.filter { it.plantShapeType.name == detailType.type }.forEach { info ->
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
                    backgroundAccessory.filter { it.itemType.name == detailType.type }.forEach { info ->
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

    data class CurrentAssetInfo(
        var isAll: Boolean,
        var assetType: AssetType
    )

    companion object {
        private const val ALL = "all"
    }
}
