package com.greener.data.di

import android.content.Context
import com.greener.data.db.BackgroundAccessoryDao
import com.greener.data.db.BackgroundAccessoryDatabase
import com.greener.data.db.PlantAccessoryDao
import com.greener.data.db.PlantAccessoryDatabase
import com.greener.data.db.PlantShapeDao
import com.greener.data.db.PlantShapeDatabase
import com.greener.data.repository.AssetRepositoryImpl
import com.greener.data.source.AssetDataSource
import com.greener.domain.repository.AssetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AssetModule {
    @Singleton
    @Provides
    fun providePlantShapeDatabase(@ApplicationContext context: Context): PlantShapeDatabase =
        PlantShapeDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providePlantShapeDao(plantShapeDatabase: PlantShapeDatabase): PlantShapeDao =
        plantShapeDatabase.plantShapeDao()

    @Singleton
    @Provides
    fun providePlantAccessoryDatabase(@ApplicationContext context: Context): PlantAccessoryDatabase =
        PlantAccessoryDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providePlantAccessoryDao(plantAccessoryDatabase: PlantAccessoryDatabase): PlantAccessoryDao =
        plantAccessoryDatabase.plantAccessoryDao()

    @Singleton
    @Provides
    fun provideBackgroundAccessoryDatabase(@ApplicationContext context: Context): BackgroundAccessoryDatabase =
        BackgroundAccessoryDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideBackgroundAccessoryDao(backgroundAccessoryDatabase: BackgroundAccessoryDatabase): BackgroundAccessoryDao =
        backgroundAccessoryDatabase.backgroundAccessoryDao()

    @Singleton
    @Provides
    fun provideAssetDataSource(
        plantShapeDatabase: PlantShapeDatabase,
        plantAccessoryDatabase: PlantAccessoryDatabase,
        backgroundAccessoryDatabase: BackgroundAccessoryDatabase
    ): AssetDataSource =
        AssetDataSource(plantShapeDatabase, plantAccessoryDatabase, backgroundAccessoryDatabase)

    @Singleton
    @Provides
    fun provideAssetRepository(
        dataSource: AssetDataSource,
        @ApplicationContext context: Context
        ): AssetRepository =
        AssetRepositoryImpl(dataSource, context)
}
