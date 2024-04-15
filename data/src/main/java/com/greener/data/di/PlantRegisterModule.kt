package com.greener.data.di

import com.greener.data.repository.PlantRegisterRepositoryImpl
import com.greener.data.service.PlantRegisterService
import com.greener.data.source.remote.PlantRegisterDataSource
import com.greener.domain.repository.PlantRegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PlantRegisterModule {
    @Provides
    @Singleton
    fun providePlantRegisterDataSource(service: PlantRegisterService): PlantRegisterDataSource =
        PlantRegisterDataSource(service)

    @Provides
    @Singleton
    fun providePlantRegisterRepository(dataSource: PlantRegisterDataSource): PlantRegisterRepository =
        PlantRegisterRepositoryImpl(dataSource)
}
