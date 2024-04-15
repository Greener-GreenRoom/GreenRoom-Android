package com.greener.domain.model.plant_register

data class PlantRegisterRequestData (
    val plantId: Long?,
    val name: String,
    val lastWatering: String,
    val wateringDuration: Int,
    val shape: String
)

