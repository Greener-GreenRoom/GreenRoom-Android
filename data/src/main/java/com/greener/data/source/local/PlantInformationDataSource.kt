package com.greener.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

class PlantInformationDataSource @Inject constructor(
    private val context: Context,
) {
    val Context.plantInformationDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "com.greener.greenRoom.plantInfo",
    )
}
