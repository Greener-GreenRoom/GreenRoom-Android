package com.greener.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlantShapeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val plantType: String,
    val plantName: String,
)
