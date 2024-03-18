package com.greener.presentation.ui.home.main

import androidx.lifecycle.ViewModel
import com.greener.domain.model.ExampleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {


    private val _myPlants = MutableStateFlow<List<ExampleModel>>(listOf())
    val myPlants: MutableStateFlow<List<ExampleModel>> get() = _myPlants

    init {
        getMyPlants()
    }

    private val _currentPlant = MutableStateFlow(
        if (_myPlants.value.isNotEmpty()) _myPlants.value[0] else null
    )
    val currentPlant: MutableStateFlow<ExampleModel?> get() = _currentPlant

    private val _isFabOpen = MutableStateFlow(false)
    val isFabOpen: MutableStateFlow<Boolean> get() = _isFabOpen

    private fun getMyPlants() {
        val plant1 = ExampleModel(
            1,
            "초롱이",
            1,
            "#000000",
            "#000000",
            1,
            1,
            2
        )
        val plant2 = ExampleModel(
            1,
            "아롱이",
            2,
            "#000000",
            "#000000",
            2,
            3,
            3
        )
        val plant3 = ExampleModel(
            1,
            "로롱이",
            3,
            "#000000",
            "#000000",
            3,
            4,
            4
        )
        val plants = listOf(plant1, plant2, plant3)
        //val plants = listOf<ExampleModel>()
        _myPlants.value = plants
    }

    fun getCountsToString(): String {
        return _myPlants.value.size.toString()
    }
    fun isAnyPlants():Boolean {
        return _myPlants.value.isNotEmpty()
    }

    fun getCounts(): Int {
        return _myPlants.value.size
    }

    fun setIsFabOpen() {
        isFabOpen.value = !isFabOpen.value
    }

    fun initFab() {
        isFabOpen.value = false
    }
}
