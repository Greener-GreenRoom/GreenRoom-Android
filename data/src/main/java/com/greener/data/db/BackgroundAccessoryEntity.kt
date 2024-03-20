package com.greener.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BackgroundAccessoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val itemType: String,
    val itemName: String,
    val limitLevel: Int
)
